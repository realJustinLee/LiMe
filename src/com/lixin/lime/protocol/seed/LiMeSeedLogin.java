package com.lixin.lime.protocol.seed;

/**
 * 登录 Seed 用于 LiMe 登录到 Server
 *
 * @author lixin
 */
public class LiMeSeedLogin extends LiMeSeed {
    /**
     * @param username 用户名
     * @param password 密码
     */
    public LiMeSeedLogin(String username, String password) {
        super(LOGIN, username, null, password, null);
    }
}
