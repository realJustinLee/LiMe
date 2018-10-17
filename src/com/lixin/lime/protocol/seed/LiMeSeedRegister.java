package com.lixin.lime.protocol.seed;


/**
 * @author lixin
 */
public class LiMeSeedRegister extends LiMeSeed {

    /**
     * @param username 用户名
     * @param password 密码
     * @param gender   性别
     * @param email    Email
     */
    public LiMeSeedRegister(String username, String password, String gender, String email) {
        super(REGISTER, username, password, gender, email);
    }
}
