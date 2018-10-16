package com.lixin.lime.client.controller;

import com.lixin.lime.protocol.exception.LiMeException;
import com.lixin.lime.protocol.seed.LiMeSeed;

public interface LiMeFarmer {
    void newLiMeMessage(LiMeSeed seed);

    void handleLiMeException(LiMeException e);
}
