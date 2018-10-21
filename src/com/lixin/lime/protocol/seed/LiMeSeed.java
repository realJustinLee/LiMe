package com.lixin.lime.protocol.seed;

import java.io.Serializable;

/**
 * LiMe 信息传输协议
 *
 * @author lixin
 */
public class LiMeSeed implements Serializable {
    /**
     * The Actions:
     * <p> Error </p>
     * ERROR_REGISTER_CONFLICT  注册信息冲突
     * ERROR_LOGIN_CONFLICT     重复登录
     * ERROR_LOGIN_PASSWORD     用户名或密码错误
     * ERROR_CONNECTION         连接错误
     * ERROR_UNKNOWN            未知错误
     * <p> Action </p>
     * MESSAGE                  发信息
     * LOGIN                    登录
     * LOGOUT                   登出
     * REGISTER                 注册
     * RECEIVER_IP              请求接收方IP
     * <p> Status </p>
     * STATUS_LOGIN_SUCCESS     登录成功
     * STATUS_REGISTER_SUCCESS  注册成功
     */
    public static final int ERROR_REGISTER_CONFLICT = -4;
    public static final int ERROR_LOGIN_CONFLICT = -3;
    public static final int ERROR_LOGIN_PASSWORD = -2;
    public static final int ERROR_CONNECTION = -1;
    public static final int ERROR_UNKNOWN = 0;

    public static final int MESSAGE = 1;
    public static final int LOGIN = 2;
    public static final int LOGOUT = 3;
    public static final int REGISTER = 4;
    public static final int RECEIVER_IP = 5;

    public static final int STATUS_LOGIN_SUCCESS = 6;
    public static final int STATUS_REGISTER_SUCCESS = 7;

    private int action;
    private String sender;
    private String receiver;
    private String message;
    private String time;

    public LiMeSeed(int action, String sender, String receiver, String message, String time) {
        this.action = action;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
    }

    public final int getAction() {
        return action;
    }

    public final String getSender() {
        return sender;
    }

    public final String getReceiver() {
        return receiver;
    }

    public final String getMessage() {
        return message;
    }

    public final String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return action + "," + sender + "," + receiver + "," + message + "," + time;
    }
}