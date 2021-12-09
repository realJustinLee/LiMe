package com.justin.lime.client.controller;

import com.justin.lime.protocol.exception.LiMeException;
import com.justin.lime.protocol.seed.LiMeSeed;

/**
 * @author Justin Lee
 */
public interface LiMeFarmer {
    /**
     * 接收到新的信息，显示在屏幕上
     *
     * @param seed 承载信息的 LiMeSeed
     */
    void newLiMeMessage(LiMeSeed seed);

    /**
     * 接收到新好友列表，显示在屏幕左侧
     *
     * @param seed 承载信息的 LiMeSeed
     */
    void newFriendList(LiMeSeed seed);

    /**
     * 接收到新的文件，选择地方存储
     *
     * @param seed 承载文件的 LiMeSeed
     */
    void newLiMeFile(LiMeSeed seed);

    /**
     * 处理由 LiMe 抛出的 LiMeException
     *
     * @param e 被抛出的 LiMeException
     */
    void handleLiMeException(LiMeException e);
}
