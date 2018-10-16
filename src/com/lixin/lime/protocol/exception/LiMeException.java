package com.lixin.lime.protocol.exception;

/**
 * @author lixin
 */
public class LiMeException extends Exception {
    private String detail;

    public LiMeException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
