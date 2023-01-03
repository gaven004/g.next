package com.g.commons.web;

import org.springframework.ui.Model;

import com.g.commons.exception.GenericAppException;


public class ControllerHelper {
    private ControllerHelper() {
    }

    public static void setSuccessMsg(Model model, String msg) {
        model.addAttribute(WebAttributes.CONTROLLER_SUCCESS_MESSAGE_KEY, msg);
    }

    public static void setErrorMsg(Model model, String msg) {
        model.addAttribute(WebAttributes.CONTROLLER_ERROR_MESSAGE_KEY, msg);
    }

    public static void setErrorMsg(Model model, String msg, Exception ex) {
        if (ex instanceof GenericAppException) {
            model.addAttribute(WebAttributes.CONTROLLER_ERROR_MESSAGE_KEY,
                    String.format("%s [详细信息：%s  | 错误代码：%s - (%s)]", msg, ex.getMessage(),
                            ((GenericAppException) ex).getCode(), ((GenericAppException) ex).getSn()));
        } else {
            model.addAttribute(WebAttributes.CONTROLLER_ERROR_MESSAGE_KEY,
                    String.format("%s [详细信息：%s]", msg, ex.getMessage()));
        }
    }
}
