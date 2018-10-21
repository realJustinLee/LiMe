package com.lixin.lime.protocol.seed;

/**
 * @author lixin
 */
public class LiMeSeedLogout extends LiMeSeed {
    public LiMeSeedLogout(String username) {
        super(LOGOUT, username, null, null, null);
    }

    public String getUsername() {
        return getSender();
    }
}
