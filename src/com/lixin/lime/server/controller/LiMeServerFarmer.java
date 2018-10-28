package com.lixin.lime.server.controller;

/**
 * @author lixin
 */
public interface LiMeServerFarmer {
    void newLog(String log);

    void newOnline();

    void newOffline();
}
