package com.lixin.test;

import com.lixin.lime.protocol.exception.LiMeException;
import com.lixin.lime.protocol.util.factory.LiMeExceptionFactory;

import static com.lixin.lime.protocol.seed.LiMeSeed.*;

/**
 * @author lixin
 */
public class MyTest {
    public static void main(String[] args) {
        MyTest test = new MyTest();
        try {
            if (test.init(true)) {
                System.out.println("OK");
            }
        } catch (LiMeException e) {
            System.out.println("NO");
        }

    }

    private boolean init(boolean does) throws LiMeException {
        if (does) {
            LiMeExceptionFactory factory = new LiMeExceptionFactory();
            throw factory.newLiMeException(ERROR_LOGIN_PASSWORD);
        }

        Thread thread = new Thread(new IncomingReader());
        thread.start();

        return true;
    }

    class IncomingReader implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello");
        }
    }
}
