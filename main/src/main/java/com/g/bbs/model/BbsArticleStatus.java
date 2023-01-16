package com.g.bbs.model;

import com.g.commons.enums.DescribableEnum;

public enum BbsArticleStatus implements DescribableEnum {
    DRAFT("草稿"), PUBLISH("发布"), WITHDRAWAL("回收");

    private final String cname;

    BbsArticleStatus(String cname) {
        this.cname = cname;
    }

    @Override
    public String getDescription() {
        return cname;
    }
}
