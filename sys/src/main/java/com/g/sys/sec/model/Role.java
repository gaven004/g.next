package com.g.sys.sec.model;

import com.g.commons.enums.DescribableEnum;

public enum Role implements DescribableEnum {
    ADMIN("系统管理员"), BOSS("老板"), OPERATOR("操作员"), GUEST("游客");

    private final String cname;

    Role(String cname) {
        this.cname = cname;
    }

    @Override
    public String getDescription() {
        return cname;
    }
}
