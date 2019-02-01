package com.g.sys.sec.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.g.sys.log.service.SysLogService;
import com.g.sys.sec.mapper.SysAuthoritiesMapper;
import com.g.sys.sec.model.SysAuthority;

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
    @Autowired
    SysLogService sysLogService;

    public Set<String> getAuthorities(String uid) {
        QueryWrapper<SysAuthority> wrapper = new QueryWrapper<>();
        wrapper.eq(SysAuthority.UID, uid);
        List<SysAuthority> list = list(wrapper);

        Set<String> set = new HashSet<>(list.size());
        for (SysAuthority item : list) {
            set.add(item.getAuthority());
        }
        return set;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insertAuthorities(String operator, String uid, Set<String> authorities) {
        for (String authority : authorities) {
            if (!saveAuthority(operator, uid, authority)) return false;
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAuthorities(String operator, String uid) {
        QueryWrapper<SysAuthority> wrapper = new QueryWrapper<>();
        wrapper.eq(SysAuthority.UID, uid);
        return removeAuthority(operator, wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateAuthorities(String operator, String uid, Set<String> authorities) {
        Set<String> original = getAuthorities(uid);

        // Del old authority
        for (String authority : original) {
            if (!authorities.contains(authority)) {
                QueryWrapper<SysAuthority> wrapper = new QueryWrapper<>();
                wrapper.eq(SysAuthority.UID, uid);
                wrapper.eq(SysAuthority.AUTHORITY, authority);
                if (!removeAuthority(operator, wrapper)) return false;
            }
        }

        // Add new authority
        for (String authority : authorities) {
            if (!original.contains(authority)) {
                if (!saveAuthority(operator, uid, authority)) return false;
            }
        }
        return true;
    }

    private boolean saveAuthority(String operator, String uid, String authority) {
        SysAuthority entity = new SysAuthority();
        entity.setUid(uid);
        entity.setAuthority(authority);
        return save(entity);
    }

    private boolean removeAuthority(String operator, QueryWrapper<SysAuthority> wrapper) {
        return remove(wrapper);
    }
}
