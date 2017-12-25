package com.g.sys.mc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.g.sys.mc.model.SysColumn;
import org.apache.ibatis.annotations.CacheNamespaceRef;

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