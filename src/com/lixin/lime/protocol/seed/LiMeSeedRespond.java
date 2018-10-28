package com.lixin.lime.protocol.seed;

import java.util.ArrayList;

/**
 * LiMeSeedRespond 用于 Server 向 LiMe 回复 type 信息
 *
 * @author lixin
 */
public class LiMeSeedRespond extends LiMeSeed {
    private ArrayList<String> limeList;

    /**
     * @param type     信息类型
     * @param sender   发送方
     * @param receiver 接收方
     */
    public LiMeSeedRespond(int type, String sender, String receiver, String message, ArrayList<String> limeList) {
        super(type, sender, receiver, message, null);
        this.limeList = limeList;
    }

    public ArrayList<String> getLimeList() {
        return limeList;
    }
}
