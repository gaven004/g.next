package com.g.sys.mc.mapper;

import org.apache.ibatis.annotations.CacheNamespaceRef;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.g.sys.mc.model.SysColumn;

/**
 * <p>
 * 系统文章栏目表 Mapper 接口
 * </p>
 *
 * @author Gaven
 * @since 2017-12-19
 */
@CacheNamespaceRef(SysColumnMapper.class)
public interface SysColumnMapper extends BaseMapper<SysColumn> {

}