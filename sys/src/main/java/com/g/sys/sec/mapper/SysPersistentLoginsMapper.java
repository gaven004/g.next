package com.g.sys.sec.mapper;

import org.apache.ibatis.annotations.CacheNamespaceRef;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.g.sys.sec.model.SysPersistentLogin;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gaven
 * @since 2017-11-27
 */
@CacheNamespaceRef(SysPersistentLoginsMapper.class)
public interface SysPersistentLoginsMapper extends BaseMapper<SysPersistentLogin> {

}