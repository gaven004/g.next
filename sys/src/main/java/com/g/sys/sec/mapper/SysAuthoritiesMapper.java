package com.g.sys.sec.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.g.sys.sec.model.SysAuthority;
import org.apache.ibatis.annotations.CacheNamespaceRef;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gaven
 * @since 2017-11-27
 */
@CacheNamespaceRef(SysAuthoritiesMapper.class)
public interface SysAuthoritiesMapper extends BaseMapper<SysAuthority> {

}