package com.justin.lime.protocol.exception;

/**
 * @author Justin Lee
 */
public class LiMeException extends Exception {
    private final String detail;

    public LiMeException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
