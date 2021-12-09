package com.justin.lime.client.model;

import com.justin.lime.client.controller.LiMeFarmer;
import com.justin.lime.client.controller.LiMeKnight;
import com.justin.lime.protocol.exception.LiMeException;
import com.justin.lime.protocol.seed.*;
import com.justin.lime.protocol.util.factory.LiMeExceptionFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.justin.lime.protocol.seed.LiMeSeed.*;
import static com.justin.lime.protocol.util.factory.LiMeStaticFactory.*;

/**
 * @author Justin Lee
 */
public class LiMeModel {
    private final String host;
    private final int port;
    private final LiMeFarmer farmer;
    private final LiMeKnight knight;

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    /**
     * CachedThreadPool 是通过 java.util.concurrent.Executors 创建的 ThreadPoolExecutor 实例
     * 这个实例会根据需要，在线程可用时，重用之前构造好的池中线程
     * 这个线程池在执行 大量短生命周期的异步任务时（many short-lived asynchronous task），可以显著提高程序性能
     * 调用 execute 时，可以重用之前已构造的可用线程，如果不存在可用线程，那么会重新创建一个新的线程并将其加入到线程池中
     * 如果线程超过 60 秒还未被使用，就会被中止并从缓存中移除。因此，线程池在长时间空闲后不会消耗任何资源
     */
    private ExecutorService cachedThreadPool;

    public LiMeModel(String host, int port, LiMeFarmer farmer, LiMeKnight knight) {
        this.host = host;
        this.port = port;
        this.farmer = farmer;
        this.knight = knight;
    }

    public synchronized void connectToServer() throws LiMeException {
        try {
            socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            throw LiMeExceptionFactory.newLiMeException(ERROR_CONNECTION);
        }
    }

    public synchronized void disconnectFromServer() throws LiMeException {
        try {
            ois.close();
            oos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw LiMeExceptionFactory.newLiMeException(ERROR_CONNECTION);
        }
    }

    public synchronized void login(String username, String password) throws LiMeException {
        screenSeed(sendAndGetSeed(new LiMeSeedLogin(username, encrypt(password))), STATUS_LOGIN_SUCCESS);
        cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new SeedGrinder());
    }

    public synchronized void register(String username, String password, String gender, String email) throws LiMeException {
        screenSeed(sendAndGetSeed(new LiMeSeedRegister(username, encrypt(password), gender, email)), STATUS_REGISTER_SUCCESS);
    }

    public synchronized void logout(String username) throws LiMeException {
        sendSeed(new LiMeSeedLogout(username));
        // 断开服务器链接，停止 SeedGrinder 的所有线程，并重新连接
        disconnectFromServer();
        cachedThreadPool.shutdownNow();
        connectToServer();
    }

    /**
     * Perhaps deprecated
     */
    public synchronized void requestFriendList(String username) throws LiMeException {
        sendSeed(new LiMeSeedRequest(FRIENDS_UPDATE, username, null));
    }

    public synchronized void sendMessage(String sender, String receiver, String message) throws LiMeException {
        String encryptedTime = encrypt(getLiMeTime());
        String encryptedMessage = encrypt(encrypt(encrypt(message, encryptedTime), sender), receiver);
        sendSeed(new LiMeSeedMessage(sender, receiver, encryptedMessage, encryptedTime));
    }

    public synchronized void sendFile(String sender, String receiver, File file) throws LiMeException {
        // 通过服务器传文件
        sendSeed(new LiMeSeedFile(sender, receiver, file));

        // TODO: 下一个版本
        //  服务器给两个LiMe发对方IP，两者建立独立TCP连接，互相传文件，不通过服务器
        //LiMeSeed seedReturn = sendAndGetSeed(new LiMeSeedRequest(RECEIVER_IP, sender, receiver));
        //screenSeed(seedReturn, RECEIVER_IP);
        //LiMeSeedRespond seedRespond = (LiMeSeedRespond) seedReturn;
        // TODO: code here
    }

    public synchronized void sendRequestForgotPassword(String sender) throws LiMeException {
        sendSeed(new LiMeSeedRequest(FORGOT_PASSWORD, sender, null));
    }

    private synchronized void sendSeed(LiMeSeed seed) throws LiMeException {
        try {
            oos.writeObject(seed);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw LiMeExceptionFactory.newLiMeException(ERROR_CONNECTION);
        }
    }

    private synchronized LiMeSeed sendAndGetSeed(LiMeSeed seed) throws LiMeException {
        try {
            oos.writeObject(seed);
            oos.flush();
            return (LiMeSeed) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw LiMeExceptionFactory.newLiMeException(ERROR_CONNECTION);
        }
    }

    private synchronized void screenSeed(LiMeSeed seed, int action) throws LiMeException {
        if (seed == null) {
            throw LiMeExceptionFactory.newLiMeException(ERROR_CONNECTION);
        } else {
            int seedAction = seed.getAction();
            if (seedAction != action) {
                throw LiMeExceptionFactory.newLiMeException(seedAction);
            }
        }
    }

    private class SeedGrinder implements Runnable {
        @Override
        public void run() {
            LiMeSeed seed;
            try {
                while ((seed = (LiMeSeed) ois.readObject()) != null) {
                    int action = seed.getAction();
                    switch (action) {
                        case ERROR_ADMIN_BANNED:
                            // 被封号
                            throw LiMeExceptionFactory.newLiMeException(ERROR_ADMIN_BANNED);
                        case ERROR_ADMIN_KICKED:
                            // 被踢
                            throw LiMeExceptionFactory.newLiMeException(ERROR_ADMIN_KICKED);
                        case MESSAGE:
                            if (seed.getReceiver().equals(LIME_GROUP_CHAT)) {
                                knight.newGroupChat(seed);
                            } else {
                                farmer.newLiMeMessage(seed);
                            }
                            break;
                        case FRIENDS_UPDATE:
                            farmer.newFriendList(seed);
                            break;
                        case FILE:
                            farmer.newLiMeFile(seed);
                            break;
                        default:
                            limeInternalError(this.getClass().getCanonicalName(), String.valueOf(action));
                            break;
                    }
                }
            } catch (SocketException e) {
                System.out.println(e.getMessage());
            } catch (LiMeException e) {
                farmer.handleLiMeException(e);
                System.exit(0);
            } catch (EOFException e) {
                farmer.handleLiMeException(LiMeExceptionFactory.newLiMeException(ERROR_CONNECTION));
                System.exit(0);
            } catch (Exception e) {
                farmer.handleLiMeException(LiMeExceptionFactory.newLiMeException(ERROR_UNKNOWN));
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
