package com.example.demo.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.WebSocketSession;
import redis.clients.jedis.Jedis;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 周杨清
 * @date 2021/2/21 20:23
 */
@Controller
@ServerEndpoint("/login/{fleaId}/{locken}")
public class LoginDownEndpoint {




    @OnOpen
    public void openSession(@PathParam("fleaId") String fleaId, @PathParam("locken") String locken, Session session){
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println();
        System.out.println();
        System.out.println("++++++++++++++++++++++++++++++++++");
        //存储用户
        //获取之前的session
        //Session session1 =  WebSocketUtil.USERS_ONLINE.get(fealId);
        System.out.println("fealId为："+fleaId+" locken为"+locken+" session为"+session);
        //上一个locken
        String locken2 = WebSocketUtil.USERS_KEY.get(fleaId);
        System.out.println("redis的locken为"+locken2);
        if (WebSocketUtil.USERS_KEY.get(fleaId)==null){
            //存储这个locken
            WebSocketUtil.USERS_KEY.put(fleaId,locken);
            //存储这个locken对应的session
            WebSocketUtil.USERS_ONLINE.put(fleaId+locken,session);
            System.out.println("存了新的feal_id");
        }else{
            if (!locken.equals(locken2)){

                System.out.println("发送了down");
                //获取上一个session
                System.out.println("session为："+WebSocketUtil.USERS_ONLINE.get(fleaId+locken2));

                WebSocketUtil.sendMessage(WebSocketUtil.USERS_ONLINE.get(fleaId+locken2),"down");
                WebSocketUtil.USERS_ONLINE.remove(fleaId+locken2);
                WebSocketUtil.USERS_ONLINE.put(fleaId+locken,session);
                WebSocketUtil.USERS_KEY.put(fleaId,locken);
            }
            System.out.println("redis的locken为"+WebSocketUtil.USERS_KEY.get(fleaId));
            /*else {
                System.out.println("发送了up");
                System.out.println("session为："+WebSocketUtil.USERS_ONLINE.get(fealId));
                WebSocketUtil.sendMessage(WebSocketUtil.USERS_ONLINE.get(fealId),"ok");
            }*/
        }
        //Gson gson = new Gson();
        //Jedis jedis = JedisPoolUtils.getJedis();
       /* if (jedis.get(fleaId)==null){
            jedis.set(fleaId,locken);
            WebSocketUtil.USERS_ONLINE.put(fleaId+locken,session);
            System.out.println("存了新的feal_id");
        }else{
            if (!locken.equals(jedis.get(fleaId))){
                String locken2=jedis.get(fleaId);
                System.out.println("发送了down");
                System.out.println("session为："+WebSocketUtil.USERS_ONLINE.get(fleaId+locken2));

                WebSocketUtil.sendMessage(WebSocketUtil.USERS_ONLINE.get(fleaId+locken2),"down");
                WebSocketUtil.USERS_ONLINE.put(fleaId+locken,session);
                jedis.set(fleaId,locken);
            }
            System.out.println("redis的fealId为"+jedis.get(fleaId));
            *//*else {
                System.out.println("发送了up");
                System.out.println("session为："+WebSocketUtil.USERS_ONLINE.get(fealId));
                WebSocketUtil.sendMessage(WebSocketUtil.USERS_ONLINE.get(fealId),"ok");
            }*//*
        }*/




        //WebSocketUtil.USERS_ONLINE.put(locken, session);
        //System.out.println("session为"+session);
        //System.out.println("locken为"+locken);
        //向所有在线用户发送用户上线通知消息
//        String message = locken;
//        System.out.println(message);
//        WebSocketUtil.sendMessageToAllOnlineUser(message);
    }

    @OnClose
    public void closeSession(@PathParam("fleaId") String fleaId, @PathParam("locken") String locken,Session session){
        //删除用户
        WebSocketUtil.USERS_ONLINE.remove(fleaId+locken);
        //向所有在线用户发送用户下线通知消息
        //String message = "["+loken+"]离开了聊天室";
        //System.out.println(message);
        //WebSocketUtil.sendMessageToAllOnlineUser(message);
        //下线后关闭session
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(@PathParam("fleaId") String fleaId,String message){
        //向聊天室中的人发送消息
//        message = "["+loken+"]：" + message;
//        System.out.println(message);
//        WebSocketUtil.sendMessageToAllOnlineUser(message);
    }
    @OnError
    public void sessionError(@PathParam("fleaId") String fleaId, @PathParam("locken") String locken,Session session, Throwable throwable){
        WebSocketUtil.USERS_ONLINE.remove(fleaId+locken);
        WebSocketUtil.USERS_KEY.remove(fleaId);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("WebSocket连接发生异常，message:"+throwable.getMessage());
    }
}
