package com.lixin.deprecated;

import com.lixin.lime.protocol.seed.LiMeSeed;

/**
 * LiMeSeed2LiMe 用于 LiMe 之间互相传递命令
 *
 * @author lixin
 */
public class LiMeSeed2LiMe extends LiMeSeed {
    /**
     * @param action   命令
     * @param sender   发送方
     * @param receiver 接收方
     * @param message  信息
     */
    public LiMeSeed2LiMe(int action, String sender, String receiver, String message) {
        super(action, sender, receiver, message, null);
    }
}
