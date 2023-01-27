package com.g.sys.msg.model;

import com.g.commons.enums.DescribableEnum;

public enum MessageTag implements DescribableEnum {
    UNREAD("未读"), READ("已读");

    private final String cname;

    MessageTag(String cname) {
        this.cname = cname;
    }

    @Override
    public String getDescription() {
        return cname;
    }
}
