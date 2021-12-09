package com.justin.lime.protocol.seed;

/**
 * LiMeSeedRequest 用于 LiMe 向 Server 请求 type 信息
 *
 * @author Justin Lee
 */
public class LiMeSeedRequest extends LiMeSeed {
    /**
     * @param type     信息类型
     * @param sender   发送方
     * @param receiver 接收方
     */
    public LiMeSeedRequest(int type, String sender, String receiver) {
        super(type, sender, receiver, null, null);
    }
}
