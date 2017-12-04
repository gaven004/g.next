package com.g.sys.dict.mapper;

import org.apache.ibatis.annotations.CacheNamespaceRef;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.g.sys.dict.model.SysDictionary;

/**
 * <p>
 * 系统字典表 Mapper 接口
 * </p>
 *
 * @author Alfred Huang
 * @since 2017-07-13
 */
@CacheNamespaceRef(SysDictionaryMapper.class)
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

}