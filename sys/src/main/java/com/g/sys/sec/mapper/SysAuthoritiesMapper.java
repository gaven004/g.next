package com.g.sys.sec.mapper;

import org.apache.ibatis.annotations.CacheNamespaceRef;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.g.sys.sec.model.SysAuthority;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gaven
 * @since 2017-11-27
 */
@CacheNamespaceRef(SysAuthoritiesMapper.class)
public interface SysAuthoritiesMapper extends BaseMapper<SysAuthority> {

}