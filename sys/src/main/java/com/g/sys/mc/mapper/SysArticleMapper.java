package com.g.sys.mc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.g.sys.mc.model.SysArticle;
import org.apache.ibatis.annotations.CacheNamespaceRef;

/**
 * <p>
 * 系统文章表 Mapper 接口
 * </p>
 *
 * @author Gaven
 * @since 2017-12-19
 */
@CacheNamespaceRef(SysArticleMapper.class)
public interface SysArticleMapper extends BaseMapper<SysArticle> {

}