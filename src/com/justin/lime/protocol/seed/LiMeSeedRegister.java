package com.justin.lime.protocol.seed;


/**
 * @author Justin Lee
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

    public String getUsername() {
        return getSender();
    }

    public String getPassword() {
        return getReceiver();
    }

    public String getGender() {
        return getMessage();
    }

    public String getEmail() {
        return getTime();
    }
}
