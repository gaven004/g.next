package com.g.sys.log.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import com.g.commons.enums.DescribableEnum;

/**
 * <p>
 * 对象变更日志
 * </p>
 *
 * @author Gaven
 * @since 2019-01-14
 */
@Data
@TableName("sys_log")
public class SysLog implements Serializable {
    private static final long serialVersionUID = -1723330177414871527L;

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

    public static final String ID = "id";
    public static final String UID = "uid";
    public static final String CTIME = "ctime";
    public static final String OPERATION = "operation";
    public static final String CLAZZ = "clazz";
    public static final String OID = "oid";
    public static final String CONTENT = "content";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private String uid;
    /**
     * 操作时间
     */
    private Date ctime;
    /**
     * 操作指令
     */
    private String operation;
    /**
     * 操作对象
     */
    private String clazz;
    /**
     * 对象主键
     */
    private String oid;
    /**
     * 内容
     */
    private String content;
    /**
     * 变更内容，不记库，按时间顺序比较content得出
     */
    transient private String diff;
}
