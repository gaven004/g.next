package com.g.bbs.model;

import com.g.commons.enums.DescribableEnum;

public enum BbsColumnStatus implements DescribableEnum {
    ENABLE("启用"), DISABLE("停用");

    private final String cname;

    BbsColumnStatus(String cname) {
        this.cname = cname;
    }

    @Override
    public String getDescription() {
        return cname;
    }
}
