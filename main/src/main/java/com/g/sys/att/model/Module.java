package com.g.sys.att.model;

public enum Module {
    COMMON("common");

    private final String path; // 上传文件保存路径

    Module(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }

}
