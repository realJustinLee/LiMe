package com.justin.lime.server.controller;

import java.util.HashSet;

/**
 * @author Justin Lee
 */
public interface LiMeServerFarmer {
    /**
     * 对新上线的用户进行操作
     *
     * @param username 用户名
     * @param limeSet  用户集
     */
    void newOnline(String username, HashSet<String> limeSet);

    /**
     * 对新下线的用户进行操作
     *
     * @param username 用户名
     * @param limeSet  用户集
     */
    void newOffline(String username, HashSet<String> limeSet);

    /**
     * 如果有用户在线，就启用踢人和Ban人权限
     *
     * @param bool 是否启用权限
     */
    void enablePrivileges(boolean bool);
}
