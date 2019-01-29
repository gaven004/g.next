package com.g.sys.mc.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 系统文章表
 * </p>
 *
 * @author Gaven
 * @since 2017-12-19
 */
@TableName("sys_article")
public class SysArticle implements Serializable {
    public static final String ID = "id";
    public static final String COLUMN_ID = "column_id";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String REDIRECTURL = "redirecturl";
    public static final String CREATE_BY = "create_by";
    public static final String CTIME = "ctime";
    public static final String UPDATE_BY = "update_by";
    public static final String MTIME = "mtime";
    public static final String SORT_ORDER = "sort_order";
    public static final String STATE = "state";
    private static final long serialVersionUID = 67686678462169102L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 栏目ID
     */
    @TableField("column_id")
    private String columnId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String body;
    /**
     * 静态URL
     */
    private String redirecturl;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建日期
     */
    private Date ctime;
    /**
     * 修改人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 修改时间
     */
    private Date mtime;
    /**
     * 排序权重，越高越排前
     */
    @TableField("sort_order")
    private Integer sortOrder;
    /**
     * 状态：编辑、发布、落画、删除
     */
    private String state;

    public String getId() {
        return id;
    }

    public SysArticle setId(String id) {
        this.id = id;
        return this;
    }

    public String getColumnId() {
        return columnId;
    }

    public SysArticle setColumnId(String columnId) {
        this.columnId = columnId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SysArticle setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public SysArticle setBody(String body) {
        this.body = body;
        return this;
    }

    public String getRedirecturl() {
        return redirecturl;
    }

    public SysArticle setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
        return this;
    }

    public String getCreateBy() {
        return createBy;
    }

    public SysArticle setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public Date getCtime() {
        return ctime;
    }

    public SysArticle setCtime(Date ctime) {
        this.ctime = ctime;
        return this;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public SysArticle setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public Date getMtime() {
        return mtime;
    }

    public SysArticle setMtime(Date mtime) {
        this.mtime = mtime;
        return this;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public SysArticle setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public String getState() {
        return state;
    }

    public SysArticle setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysArticle [");
        builder.append("id=").append(id).append(", ");
        builder.append("columnId=").append(columnId).append(", ");
        builder.append("title=").append(title).append(", ");
        builder.append("body=").append(body).append(", ");
        builder.append("redirecturl=").append(redirecturl).append(", ");
        builder.append("createBy=").append(createBy).append(", ");
        builder.append("ctime=").append(ctime).append(", ");
        builder.append("updateBy=").append(updateBy).append(", ");
        builder.append("mtime=").append(mtime).append(", ");
        builder.append("sortOrder=").append(sortOrder).append(", ");
        builder.append("state=").append(state);
        builder.append("]");
        return builder.toString();
    }
}
