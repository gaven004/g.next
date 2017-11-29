package com.g.commons.exception;

import java.util.UUID;

/**
 * 通用系统异常
 * 
 * 每一个系统异常定义错误码，前台可以根据代码显示对应的错误信息，另外也便于定位错误的出处
 * 
 * errorCode规则：前缀[CORE...] + 模块[] + 错误码
 * 
 * @version 1.0, 2015-7-3, Gaven
 * @version 1.1, 2016-6-28, Gaven, 增加sn属性，异常唯一标识，用于日志跟踪
 * @version 2.0, 2017-7-18, Gaven, 更改message的格式
 * @version 3.0, 2017-10-24, Gaven, 恢复message的格式，增加getDetail方法
 */
public class GenericAppException extends RuntimeException {
	private static final long serialVersionUID = -1925535512076669332L;

	private String errorCode;

	private String sn; // 异常唯一标识，用于日志跟踪

	public GenericAppException(String message) {
		this("CORE-00-0000", UUID.randomUUID().toString(), message);
	}

	public GenericAppException(String errorCode, String message) {
		this(errorCode, UUID.randomUUID().toString(), message);
	}

	public GenericAppException(String errorCode, String sn, String message) {
		super(message);
		this.errorCode = errorCode;
		this.sn = sn;
	}

	public GenericAppException(String message, Throwable cause) {
		this("CORE-00-0000", UUID.randomUUID().toString(), message, cause);
	}

	public GenericAppException(String errorCode, String message, Throwable cause) {
		this(errorCode, UUID.randomUUID().toString(), message, cause);
	}

	public GenericAppException(String errorCode, String sn, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.sn = sn;
	}
	
	public String getDetail() {
	    return String.format("code: %s, sn: %s, message: %s", errorCode, sn, getMessage());
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getSn() {
		return sn;
	}
}
