// Generated 2021年1月19日 上午11:12:12 by Hibernate Tools 6.0.0.Alpha2

package com.g.sys.sec.model;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.g.commons.model.AbstractEntity;

/**
 * SysRoleMembers generated by hbm2java
 */
@Entity
@Table(name = "sys_role_members")
@DynamicInsert
@DynamicUpdate
public class SysRoleMembers extends AbstractEntity implements java.io.Serializable {

    private SysRoleMembersId id;

    public SysRoleMembers() {
    }

    public SysRoleMembers(SysRoleMembersId id) {
        this.id = id;
    }

    @EmbeddedId

    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
            @AttributeOverride(name = "roleId", column = @Column(name = "role_id", nullable = false))})
    public SysRoleMembersId getId() {
        return this.id;
    }

    public void setId(SysRoleMembersId id) {
        this.id = id;
    }


}


