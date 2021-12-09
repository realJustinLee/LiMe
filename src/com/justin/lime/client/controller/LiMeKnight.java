package com.justin.lime.client.controller;

import com.justin.lime.protocol.seed.LiMeSeed;

/**
 * @author Justin Lee
 */
public interface LiMeKnight {
    /**
     * 接收到新的信息，显示在屏幕上
     *
     * @param seed 承载信息的 LiMeSeed
     */
    void newGroupChat(LiMeSeed seed);
}
