package com.lixin.lime.protocol.seed;

import java.util.Set;

/**
 * LiMeSeedRespond 用于 Server 向 LiMe 回复 type 信息
 *
 * @author lixin
 */
public class LiMeSeedRespond extends LiMeSeed {
    private Set<String> limeSet;

    /**
     * @param type     信息类型
     * @param sender   发送方
     * @param receiver 接收方
     */
    public LiMeSeedRespond(int type, String sender, String receiver, String message, Set<String> limeSet) {
        super(type, sender, receiver, message, null);
        this.limeSet = limeSet;
    }

    public Set<String> getLimeSet() {
        return limeSet;
    }
}
