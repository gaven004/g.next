package com.g.sys.sec.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.g.sys.sec.model.SysPersistentLogin;
import org.apache.ibatis.annotations.CacheNamespaceRef;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gaven
 * @since 2017-11-27
 */
@CacheNamespaceRef(SysPersistentLoginsMapper.class)
public interface SysPersistentLoginsMapper extends BaseMapper<SysPersistentLogin> {

}