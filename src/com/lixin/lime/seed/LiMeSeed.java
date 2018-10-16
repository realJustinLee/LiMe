package com.lixin.lime.seed;

/**
 * @author lixin
 */
public class LiMeSeed {
    /**
     * The Actions:
     * <p>
     * ERROR_REGISTER_CONFLICT  注册信息冲突
     * ERROR_LOGIN_CONFLICT     重复登录
     * ERROR_LOGIN_PASSWORD     用户名密码错误
     * ERROR_UNKNOWN            未知错误
     * MESSAGE                  发信息
     * LOGIN                    登录
     * LOGIN_SUCCESS            登录成功
     * REGISTER                 注册
     * REGISTER_SUCCESS         注册成功
     */
    public static final int ERROR_REGISTER_CONFLICT = -4;
    public static final int ERROR_LOGIN_CONFLICT = -3;
    public static final int ERROR_LOGIN_PASSWORD = -2;
    public static final int ERROR_UNKNOWN = -1;
    public static final int MESSAGE = 0;
    public static final int LOGIN = 1;
    public static final int LOGIN_SUCCESS = 2;
    public static final int REGISTER = 3;
    public static final int REGISTER_SUCCESS = 4;

    private int action;
    private String sender;
    private String receiver;
    private String message;
    private String time;

    public LiMeSeed(String username, String password) {
        action = LOGIN;
        sender = username;
        message = password;
    }

    public LiMeSeed(String sender, String receiver, String message, String time) {
        action = MESSAGE;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
    }

    public LiMeSeed(int action, String sender, String receiver, String message, String time) {
        // action = REGISTER 复用 (REGISTER, String username, String password, String gender, String email)
        this.action = action;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return action + "," + sender + "," + receiver + "," + message;
    }
}