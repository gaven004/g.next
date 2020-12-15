package com.g.sys.log;

import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;

import com.g.commons.enums.DescribableEnum;
import com.g.commons.model.AbstractEntity;

@Entity
@Table(name = "sys_log")
@DynamicInsert
public class SysLog extends AbstractEntity<Long> {
    public static final Long SYSTEM_USER = 0L; // SYSTEM

    public enum OPERATIONS implements DescribableEnum {
        CREATE("新建"), UPDATE("修改"), DELETE("删除");

        private final String cname;

        OPERATIONS(String cname) {
            this.cname = cname;
        }

        @Override
        public String getDescription() {
            return cname;
        }
    }

    private Long id;
    private Long uid;
    /**
     * 操作人名称
     */
    private String operator;
    private Timestamp ctime;
    private String operation;
    private String clazz;
    private String oid;
    private String content;
    /**
     * 变更内容，不记库，按时间顺序比较content得出
     */
    private String diff;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid")
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Transient
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "ctime")
    public Timestamp getCtime() {
        return ctime;
    }

    public void setCtime(Timestamp ctime) {
        this.ctime = ctime;
    }

    @Basic
    @Column(name = "operation")
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Basic
    @Column(name = "clazz")
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Basic
    @Column(name = "oid")
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Transient
    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysLog sysLog = (SysLog) o;
        return Objects.equals(id, sysLog.id) &&
                Objects.equals(uid, sysLog.uid) &&
                Objects.equals(ctime, sysLog.ctime) &&
                Objects.equals(operation, sysLog.operation) &&
                Objects.equals(clazz, sysLog.clazz) &&
                Objects.equals(oid, sysLog.oid) &&
                Objects.equals(content, sysLog.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
