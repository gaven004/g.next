package com.g.commons.model;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * r
 * jqgrid分页参数 增加jqgrid传入的排序字段 增加排序字段驼峰形式自动转成下划线形式
 * jqgrid只支持单字段排序，而MP从3.0开始，支持多字段排序，V1.1做适应性修改
 *
 * @version 1.1 2019/1/18, By Gaven
 */
public class BindingPage<T> extends Page<T> {
    private static final long serialVersionUID = -918312364976356742L;

    private String sidx;
    private String sord;
    private boolean convertOrderBy = true;

    /**
     * jqgrid设置排序字段
     *
     * @param sidx
     */
    public void setSidx(String sidx) {
        this.sidx = convertOrderBy ? StringUtils.camelToUnderline(sidx) : (StringUtils.isEmpty(sidx) ? "" : sidx);
        setOrderByField();
    }

    /**
     * jqgrid设置排序方式
     *
     * @param sord asc、desc
     */
    public void setSord(String sord) {
        this.sord = sord;
        setOrderByField();
    }

    private void setOrderByField() {
        if (StringUtils.isEmpty(sord) || "ASC".equalsIgnoreCase(sord)) {
            setAsc(sidx);
            setDesc(null);
        } else {
            setDesc(sidx);
            setAsc(null);
        }
    }

    /**
     * 是否把order by字段转换成下划线形式
     *
     * @param convertOrderBy
     */
    public void setConvertOrderBy(boolean convertOrderBy) {
        this.convertOrderBy = convertOrderBy;
    }

    public String getOrderByField() {
        String orderByField = "";

        if (ascs() != null && ascs().length > 0 && !StringUtils.isEmpty(ascs()[0])) {
            orderByField = ascs()[0];
        } else if (descs() != null && descs().length > 0 && !StringUtils.isEmpty(descs()[0])) {
            orderByField = descs()[0];
        }

        if (convertOrderBy) {
            return StringUtils.camelToUnderline(orderByField);
        }
        return orderByField;
    }
}
