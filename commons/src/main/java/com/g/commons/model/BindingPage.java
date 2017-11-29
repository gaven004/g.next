package com.g.commons.model;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * 分页参数 增加jqgrid传入的排序字段 增加排序字段驼峰形式自动转成下划线形式
 *
 * @author zhongsh
 * @version 2017/7/18
 * @since 1.0
 */
public class BindingPage<T> extends Page<T> {
    private static final long serialVersionUID = -918312364976356742L;

    private boolean convertOrderBy = true;

    /**
     * jqgrid设置排序字段
     *
     * @param sidx
     */
    public void setSidx(String sidx) {
        setOrderByField(sidx);
    }

    /**
     * jqgrid设置排序方式
     *
     * @param sord
     *            asc、desc
     */
    public void setSord(String sord) {
        if (StringUtils.isEmpty(sord)) {
            return;
        }
        if ("ASC".equalsIgnoreCase(sord)) {
            setAsc(true);
        } else {
            setAsc(false);
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

    @Override
    public String getOrderByField() {
        String orderByField = super.getOrderByField();
        if (convertOrderBy) {
            return StringUtils.camelToUnderline(orderByField);
        }
        return orderByField;
    }
}
