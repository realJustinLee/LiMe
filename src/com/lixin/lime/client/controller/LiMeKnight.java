package com.lixin.lime.client.controller;

/**
 * @author lixin
 */
public interface LiMeKnight {
    /**
     * 接收到新的信息，显示在屏幕上
     *
     * @param sender    发信方
     * @param time      时间戳
     * @param message   信息
     */
    void newGroupChat(String sender, String time, String message);
}
