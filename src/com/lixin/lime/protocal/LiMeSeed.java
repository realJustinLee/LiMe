package com.lixin.lime.protocal;

/**
 * @author lixin
 */
public class LiMeSeed {
    private static final int ERROR_LOGIN_CONFLICT = -3;
    private static final int ERROR_LOGIN_PASSWORD = -2;
    private static final int ERROR_UNKONWN = -1;
    private static final int MESSAGE = 0;
    private static final int LOGIN = 1;
    private static final int LOGIN_SUCCESS = 2;

    private int action;
    private String sender;
    private String receiver;
    private String message;

    public LiMeSeed(String username, String password) {
        action = LOGIN;
        sender = username;
        message = password;
    }

    public LiMeSeed(String sender, String receiver, String message) {
        action = MESSAGE;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public LiMeSeed(int action, String sender, String receiver, String message) {
        this.action = action;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public LiMeSeed(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return action + "," + sender + "," + receiver + "," + message;
    }
}