package com.g.sys.sec.service;

import com.g.sys.sec.model.SysAuthority;
import com.g.sys.sec.mapper.SysAuthoritiesMapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gaven
 * @since 2017-11-27
 */
@Service
public class SysAuthoritiesService extends ServiceImpl<SysAuthoritiesMapper, SysAuthority> {
    public Set<String> getAuthorities(String uid) {
        Wrapper<SysAuthority> wrapper = new EntityWrapper<>();
        wrapper.eq(SysAuthority.UID, uid);
        List<SysAuthority> list = selectList(wrapper);

        Set<String> set = new HashSet<>(list.size());
        for (SysAuthority item : list) {
            set.add(item.getAuthority());
        }
        return set;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insertAuthorities(String uid, Set<String> authorities) {
        for (String authority : authorities) {
            SysAuthority entity = new SysAuthority();
            entity.setUid(uid);
            entity.setAuthority(authority);
            if (!insert(entity)) {
                return false;
            }
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAuthorities(String uid) {
        Wrapper<SysAuthority> wrapper = new EntityWrapper<>();
        wrapper.eq(SysAuthority.UID, uid);
        return delete(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateAuthorities(String uid, Set<String> authorities) {
        Set<String> original = getAuthorities(uid);

        // Del old authority
        for (String authority : original) {
            if (!authorities.contains(authority)) {
                Wrapper<SysAuthority> wrapper = new EntityWrapper<>();
                wrapper.eq(SysAuthority.UID, uid);
                wrapper.eq(SysAuthority.AUTHORITY, authority);
                delete(wrapper);
            }
        }

        // Add new authority
        for (String authority : authorities) {
            if (!original.contains(authority)) {
                SysAuthority entity = new SysAuthority();
                entity.setUid(uid);
                entity.setAuthority(authority);
                if (!insert(entity)) {
                    return false;
                }
            }
        }
        return true;
    }
}
