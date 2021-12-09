package com.justin.lime.protocol.seed;

/**
 * @author Justin Lee
 */
public class LiMeSeedLogout extends LiMeSeed {
    public LiMeSeedLogout(String username) {
        super(LOGOUT, username, null, null, null);
    }

    public String getUsername() {
        return getSender();
    }
}
