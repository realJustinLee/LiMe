package com.lixin.lime.protocol.util.factory;

import com.lixin.lime.protocol.exception.LiMeException;

import static com.lixin.lime.protocol.seed.LiMeSeed.*;
import static com.lixin.lime.protocol.util.factory.MyStaticFactory.*;

/**
 * @author lixin
 */
public class LiMeExceptionFactory {

    /**
     * @param errorCode statics from LiMeSeed
     *                  <p> Error from Admin</p>
     *                  ERROR_ADMIN_BANNED       被封号
     *                  ERROR_ADMIN_KICKED       被踢
     *                  <p> Error from inside</p>
     *                  ERROR_REGISTER_CONFLICT  注册信息冲突
     *                  ERROR_LOGIN_CONFLICT     重复登录
     *                  ERROR_LOGIN_PASSWORD     用户名或密码错误
     *                  ERROR_CONNECTION         连接错误
     *                  ERROR_UNKNOWN            未知错误
     */
    public LiMeException newLiMeException(int errorCode) {
        switch (errorCode) {
            case ERROR_ADMIN_BANNED:
                return new LiMeException("账号被被封禁", "您因违反 " + THE_BRAND + " 用户协议，账号已被永久封禁");
            case ERROR_ADMIN_KICKED:
                return new LiMeException("账号被踢", "您已被 " + THE_BRAND + " 管理员从服务器中移除");
            case ERROR_REGISTER_CONFLICT:
                return new LiMeException("注册信息冲突", "您的用户名或者邮箱已被用于注册 " + THE_BRAND);
            case ERROR_LOGIN_CONFLICT:
                return new LiMeException("重复登录", "您已在另一计算机上登录 " + THE_BRAND);
            case ERROR_LOGIN_PASSWORD:
                return new LiMeException("用户名或密码错误", "请输入正确的用户名和密码");
            case ERROR_CONNECTION:
                return new LiMeException("连接错误", THE_BRAND + " 找不到服务器，请检查网络连接\n" +
                        "正在使用的服务器地址为 <" + WEB_MOST + "> 如需自建服务器请参阅官网文档");
            case ERROR_UNKNOWN:
                return new LiMeException("未知错误", THE_BRAND + " 发生了一个未知错误");
            default:
                limeInternalError(this.getClass().getCanonicalName(), String.valueOf(errorCode));
                return new LiMeException("未知错误", THE_BRAND + " 发生了一个未知错误");
        }
    }
}
