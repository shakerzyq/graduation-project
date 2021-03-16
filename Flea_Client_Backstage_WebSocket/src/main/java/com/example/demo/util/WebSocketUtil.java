package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 周杨清
 * @date 2021/2/21 18:47
 */
@Component
public class WebSocketUtil {
    /**
     * 模拟存储   在线用户
     */
    public static final Map<String, Session> USERS_ONLINE = new ConcurrentHashMap<>();
    public static final Map<String, String> USERS_KEY = new ConcurrentHashMap<>();

    /**
     * 向所有在线用户发送消息(遍历 向每一个用户发送)
     * @param message
     */
    public static void sendMessageToAllOnlineUser(String message){
        USERS_ONLINE.forEach((username, Session) -> sendMessage(Session, message));
    }

    /**
     * 向指定用户发送消息
     * @param session 用户session
     * @param message 发送消息内容
     */
//    public static void sendMessage(Session session, String message) {
//        if (session == null) {
//            return;
//        }
//
//        final RemoteEndpoint.Basic basic = session.getBasicRemote();
//        if (basic == null) {
//            return;
//        }
//
//        try {
//            basic.sendText(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static void sendMessage(Session session, String message) {
        if (session == null) {
            return;
        }

        final RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            return;
        }

        try {
            basic.sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
