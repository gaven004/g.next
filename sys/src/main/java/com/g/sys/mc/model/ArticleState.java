package com.g.sys.mc.model;

import com.g.commons.enums.DescribableEnum;

public enum ArticleState implements DescribableEnum {
    MODIFIED("编辑"), PUBLISH("发布"), DOWN("落画"), DELETED("删除");

    private final String cname;

    ArticleState(String cname) {
        this.cname = cname;
    }

    @Override
    public String getDescription() {
        return cname;
    }
}
