package com.justin.lime.protocol.util.factory;

import com.justin.lime.protocol.exception.LiMeException;

import static com.justin.lime.protocol.seed.LiMeSeed.*;
import static com.justin.lime.protocol.util.factory.LiMeStaticFactory.*;


/**
 * @author Justin Lee
 */
public class LiMeExceptionFactory {

    /**
     * @param errorCode statics from LiMeSeed
     *                  <p> Error from Admin</p>
     *                  ERROR_ADMIN_BANNED       被封号
     *                  ERROR_ADMIN_KICKED       被踢
     *                  <p> Error from inside</p>
     *                  ERROR_CONFIG_FILE      设置文件无法读写
     *                  ERROR_REGISTER_CONFLICT  注册信息冲突
     *                  ERROR_LOGIN_CONFLICT     重复登录
     *                  ERROR_LOGIN_PASSWORD     用户名或密码错误
     *                  ERROR_CONNECTION         连接错误
     *                  ERROR_UNKNOWN            未知错误
     */
    public static LiMeException newLiMeException(int errorCode) {
        return switch (errorCode) {
            case ERROR_ADMIN_BANNED ->
                    new LiMeException("账号被被封禁", "您因违反 " + THE_BRAND + " 用户协议，账号已被永久封禁");
            case ERROR_ADMIN_KICKED -> new LiMeException("账号被踢", "您已被 " + THE_BRAND + " 管理员从服务器中移除");
            case ERROR_CONFIG_FILE -> new LiMeException("无法创建或修改设置文件", "无法创建或修改设置文件");
            case ERROR_UPDATE_CONFIG -> new LiMeException("Config File Not Initiated", "Please amend the config file");
            case ERROR_REGISTER_CONFLICT ->
                    new LiMeException("注册信息冲突", "您的用户名或者邮箱已被用于注册 " + THE_BRAND);
            case ERROR_LOGIN_CONFLICT -> new LiMeException("重复登录", "您已在另一计算机上登录 " + THE_BRAND);
            case ERROR_LOGIN_PASSWORD -> new LiMeException("用户名或密码错误", "请输入正确的用户名和密码");
            case ERROR_CONNECTION -> new LiMeException("连接错误", THE_BRAND + " 找不到服务器，请检查网络连接\n" +
                    "正在使用的服务器地址为 <" + URL_LIME_HOMEPAGE + "> 如需自建服务器请参阅官网文档");
            case ERROR_UNKNOWN -> new LiMeException("未知错误", THE_BRAND + " 发生了一个未知错误");
            default -> {
                limeInternalError(LiMeExceptionFactory.class.getCanonicalName(), String.valueOf(errorCode));
                yield new LiMeException("未知错误", THE_BRAND + " 发生了一个未知错误");
            }
        };
    }
}
