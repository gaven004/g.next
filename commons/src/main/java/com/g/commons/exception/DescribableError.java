package com.g.commons.exception;

/**
 * 提供描述字符串的枚举，如中文含义
 *
 * @author zhongsh
 * @version 2017/5/26
 */
public interface DescribableError {
    String code();
    String message();
}
