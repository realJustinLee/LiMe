package com.justin.lime.protocol.seed;

/**
 * @author Justin Lee
 */
public class LiMeSeedMessage extends LiMeSeed {
    public LiMeSeedMessage(String sender, String receiver, String message, String time) {
        super(MESSAGE, sender, receiver, message, time);
    }
}
