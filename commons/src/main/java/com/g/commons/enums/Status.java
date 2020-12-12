package com.g.commons.enums;

public enum Status implements DescribableEnum {
    VALID("有效"), INVALID("无效");

    private final String cname;

    Status(String cname) {
        this.cname = cname;
    }

    @Override
    public String getDescription() {
        return cname;
    }
}
