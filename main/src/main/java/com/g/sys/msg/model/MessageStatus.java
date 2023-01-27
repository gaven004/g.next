package com.g.sys.msg.model;

import com.g.commons.enums.DescribableEnum;

public enum MessageStatus implements DescribableEnum {
    DRAFT("草稿"), SENT("已发送"), DELETED("删除");

    private final String cname;

    MessageStatus(String cname) {
        this.cname = cname;
    }

    @Override
    public String getDescription() {
        return cname;
    }
}
