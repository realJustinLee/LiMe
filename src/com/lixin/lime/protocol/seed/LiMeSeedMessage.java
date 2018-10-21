package com.lixin.lime.protocol.seed;

/**
 * @author lixin
 */
public class LiMeSeedMessage extends LiMeSeed {
    public LiMeSeedMessage(String sender, String receiver, String message, String time) {
        super(MESSAGE, sender, receiver, message, time);
    }
}
