/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.g.commons.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;

/**
 * MyBatis-Plus的分页插件，是StatementHandler的切入，在StatementHandler执行时才修改boundSql.sql
 * 及boundSql.parameterMappings实现数据端分页。
 *
 * 开启MyBatis的缓存时，则会使用CachingExecutor来执行查询，在StatementHandler介入前，判断是否存在缓存，存在则直接返回。
 * 缓存的KEY由MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql几个参数构成。
 * 显然，MyBatis-Plus的分页插件此时还未调用，所以KEY中未带有分页信息。
 * 因此增加CachingExecutor的拦截器，重新构建缓存的KEY，以适应分页插件的使用
 */

@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class CachePaginationInterceptor extends PaginationInterceptor implements Interceptor {
    /**
     * 方言类型
     */
    private String dialectType;
    /**
     * 方言实现类
     */
    private String dialectClazz;

    /**
     * Physical Pagination Interceptor for all the queries with parameter
     * {@link org.apache.ibatis.session.RowBounds}
     */
    public Object intercept(Invocation invocation) throws Throwable {
        CachingExecutor cachingExecutor = PluginUtils.realTarget(invocation.getTarget());
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object paramObj = invocation.getArgs()[1];
        ResultHandler resultHandler = (ResultHandler) invocation.getArgs()[3];

        // 判断参数里是否有page对象
        IPage page = null;
        if (paramObj instanceof IPage) {
            page = (IPage) paramObj;
        } else if (paramObj instanceof Map) {
            for (Object arg : ((Map) paramObj).values()) {
                if (arg instanceof IPage) {
                    page = (IPage) arg;
                    break;
                }
            }
        }

        /**
         * 不需要分页的场合，如果 size 小于 0 返回结果集
         */
        if (null == page || page.getSize() < 0) {
            return invocation.proceed();
        }

        // bug fix：二级缓存的key需要用分页sql和分页RowBounds重新构造
        Configuration configuration = mappedStatement.getConfiguration();
        Connection connection = configuration.getEnvironment().getDataSource().getConnection();
        DbType dbType = StringUtils.isNotEmpty(dialectType) ? DbType.getDbType(dialectType)
                : JdbcUtils.getDbType(connection.getMetaData().getURL());
        connection.close();

        BoundSql boundSql = mappedStatement.getBoundSql(paramObj);
        List<ParameterMapping> mappings = new ArrayList<>(boundSql.getParameterMappings());
        MetaObject metaObject = SystemMetaObject.forObject(boundSql);
        Map<String, Object> additionalParameters = (Map<String, Object>) metaObject.getValue("additionalParameters");

        String originalSql = boundSql.getSql();
        String buildSql = concatOrderBy(originalSql, page, true);

        DialectModel model = DialectFactory.buildPaginationSql(page, buildSql, dbType, dialectClazz);
        model.consumers(mappings, configuration, additionalParameters);

        BoundSql cacheBoundSql = new BoundSql(configuration, model.getDialectSql(), mappings, paramObj);
        additionalParameters.forEach((name, value) -> cacheBoundSql.setAdditionalParameter(name, value));

        // 生成二级缓存的key（重点！默认invocation.proceed()会调用CachingExecutor的另外一个query方法，
        // 因为翻页时传入的BoundSql和RowBounds一样，所以生成的key一样，导致翻页时总数能从二级缓存取到数据）
        CacheKey cacheKey = cachingExecutor.createCacheKey(mappedStatement, paramObj, RowBounds.DEFAULT, cacheBoundSql);

        // 执行查询。因为查询语句已经带了limit，所以RowBounds传入RowBounds.DEFAULT
        return cachingExecutor.query(mappedStatement, paramObj, RowBounds.DEFAULT, resultHandler, cacheKey, boundSql);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof CachingExecutor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");
        if (StringUtils.isNotEmpty(dialectType)) {
            this.dialectType = dialectType;
        }
        if (StringUtils.isNotEmpty(dialectClazz)) {
            this.dialectClazz = dialectClazz;
        }
    }
}
