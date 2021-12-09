package com.justin.lime.protocol.seed;

import java.io.Serializable;

/**
 * LiMe 信息传输协议
 *
 * @author Justin Lee
 */
public class LiMeSeed implements Serializable {
    /**
     * The Actions:
     * <p> Error from Admin</p>
     * ERROR_ADMIN_BANNED       被封号
     * ERROR_ADMIN_KICKED       被踢
     * <p> Error from inside</p>
     * ERROR_CONFIG_FILE        设置文件无法读写
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
     * ALL_LIME                 请求所有在线用户
     * <p> Status </p>
     * STATUS_LOGIN_SUCCESS     登录成功
     * STATUS_REGISTER_SUCCESS  注册成功
     */
    public static final int ERROR_ADMIN_BANNED = -7;
    public static final int ERROR_ADMIN_KICKED = -6;

    public static final int ERROR_CONFIG_FILE = -5;
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
    public static final int FRIENDS_UPDATE = 6;
    public static final int FILE = 7;
    public static final int FORGOT_PASSWORD = 8;

    public static final int STATUS_LOGIN_SUCCESS = 9;
    public static final int STATUS_REGISTER_SUCCESS = 10;

    public static final String LIME_GROUP_CHAT = "[ LiMe Group Chat ]";

    private final int action;
    private final String sender;
    private final String receiver;
    private final String message;
    private final String time;

    public LiMeSeed(int action, String sender, String receiver, String message, String time) {
        this.action = action;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
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
        return action + "," + sender + "," + receiver + "," + message + "," + time;
    }
}