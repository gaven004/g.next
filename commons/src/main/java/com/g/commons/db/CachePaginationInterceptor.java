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
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.plugins.parser.ISqlParser;
import com.baomidou.mybatisplus.plugins.parser.SqlInfo;
import com.baomidou.mybatisplus.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.toolkit.SqlUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * 拷贝修改：com.baomidou.mybatisplus.plugins.CachePaginationInterceptor 修复生成两个ORDER
 * BY问题 修复开启二级缓存时分页查询，翻页数据不变的问题（生成的CacheKey相同）
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class CachePaginationInterceptor extends PaginationInterceptor implements Interceptor {

    /* 溢出总页数，设置第一页 */
    private boolean overflowCurrent = false;
    // COUNT SQL 解析
    private ISqlParser sqlParser;
    /* 方言类型 */
    private String dialectType;
    /* 方言实现类 */
    private String dialectClazz;

    /**
     * Physical Pagination Interceptor for all the queries with parameter
     * {@link org.apache.ibatis.session.RowBounds}
     */
    public Object intercept(Invocation invocation) throws Throwable {

        Object target = invocation.getTarget();
        if (target instanceof StatementHandler) {
            return super.intercept(invocation);
        } else {
            RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
            if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
                return invocation.proceed();
            }
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Executor executor = (Executor) invocation.getTarget();
            Connection connection = executor.getTransaction().getConnection();
            Object parameterObject = invocation.getArgs()[1];
            BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
            String originalSql = boundSql.getSql();
            if (rowBounds instanceof Pagination) {
                Pagination page = (Pagination) rowBounds;
                if (page.isSearchCount()) {
                    SqlInfo sqlInfo = SqlUtils.getCountOptimize(sqlParser, originalSql);
                    super.queryTotal(overflowCurrent, sqlInfo.getSql(), mappedStatement, boundSql, page, connection);
                    // if (page.getTotal() <= 0) {
                    // return invocation.proceed();
                    // }
                }
                // bug fix：二级缓存的key需要用分页sql和分页RowBounds重新构造
                if (invocation.getTarget() instanceof CachingExecutor) {
                    CachingExecutor cachingExecutor = (CachingExecutor) invocation.getTarget();
                    ResultHandler resultHandler = (ResultHandler) invocation.getArgs()[3];
                    DBType dbType = StringUtils.isNotEmpty(dialectType) ? DBType.getDBType(dialectType)
                            : JdbcUtils.getDbType(connection.getMetaData().getURL());
                    // 组装分页sql，加上limit语句
                    String pageSql = DialectFactory.buildPaginationSql(page, boundSql.getSql(), dbType, dialectClazz);
                    // 用于生成CacheKey的RowBounds
                    RowBounds rb = new RowBounds(page.getOffsetCurrent(), page.getSize());
                    // 重新构造BoundSql，使用分页sql
                    BoundSql pageBoundSql = new BoundSql(mappedStatement.getConfiguration(), pageSql,
                            boundSql.getParameterMappings(), parameterObject);
                    // 生成二级缓存的key（重点！默认invocation.proceed()会调用CachingExecutor的另外一个query方法，因为翻页时传入的BoundSql和RowBounds一样，所以生成的key一样，导致翻页时总数能从二级缓存取到数据）
                    CacheKey pageKey = cachingExecutor.createCacheKey(mappedStatement, parameterObject, rb,
                            pageBoundSql);
                    // 执行查询。因为查询语句已经带了limit，所以RowBounds传入RowBounds.DEFAULT
                    return cachingExecutor.query(mappedStatement, parameterObject, RowBounds.DEFAULT, resultHandler,
                            pageKey, pageBoundSql);
                }
            }

        }
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

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

    public CachePaginationInterceptor setDialectType(String dialectType) {
        this.dialectType = dialectType;
        return this;
    }

    public CachePaginationInterceptor setSqlParser(ISqlParser sqlParser) {
        this.sqlParser = sqlParser;
        return this;
    }

    public CachePaginationInterceptor setOverflowCurrent(boolean overflowCurrent) {
        this.overflowCurrent = overflowCurrent;
        return this;
    }

}
