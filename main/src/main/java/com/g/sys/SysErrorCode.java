package com.g.sys;

import com.g.commons.exception.DescribableError;

/**
 * 系统异常代码
 * <p>
 * code规则：前缀[CORE...] + 模块[] + 错误码
 *
 * @version 1.0, 2020-12-15, Gaven
 */

public enum SysErrorCode implements DescribableError {
    PathCreateFailed("CORE-00-0103", "创建目录失败"),
    FileSaveFailed("CORE-00-0104", "无法保存文件"),
    FileDeleteFailed("CORE-00-0105", "删除文件失败");

    private final String code;
    private final String message;

    SysErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
