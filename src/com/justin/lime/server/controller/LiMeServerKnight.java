package com.justin.lime.server.controller;

import com.justin.lime.protocol.seed.LiMeSeed;

/**
 * @author Justin Lee
 */
public interface LiMeServerKnight {
    /**
     * 接收到新的信息，显示在屏幕上
     *
     * @param seed 承载信息的 LiMeSeed
     */
    void newChatHistory(LiMeSeed seed);
}
