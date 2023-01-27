package com.g.sys.att.model;

public enum Module {
    COMMON("common"),
    BBS_IMG("bbs/img"),
    BBS_ATTACHMENT("bbs/attachment"),
    MSG_IMG("msg/img"),
    MSG_ATTACHMENT("msg/attachment");

    private final String path; // 上传文件保存路径

    Module(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }

}
