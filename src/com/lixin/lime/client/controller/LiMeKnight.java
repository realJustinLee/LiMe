package com.lixin.lime.client.controller;

import com.lixin.lime.protocol.seed.LiMeSeed;

/**
 * @author lixin
 */
public interface LiMeKnight {
    /**
     * 接收到新的信息，显示在屏幕上
     *
     * @param seed 承载信息的 LiMeSeed
     */
    void newGroupChat(LiMeSeed seed);
}
