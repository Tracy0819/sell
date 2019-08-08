package com.imooc.sell.Service.Impl;

import javassist.tools.web.Webserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;
    //定义websocket的容器来储存这些session
    private static CopyOnWriteArraySet<WebSocket> webSocketsSet = new CopyOnWriteArraySet<>();
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketsSet.add(this);
        log.info("[websocket消息] 有心的连接来啦。。。。总数：{}",webSocketsSet.size());
    }

    @OnClose
    public void onClose() {
        webSocketsSet.remove(this);
        log.info("[websockt消息] 连接断开啦。。。。总数{}",webSocketsSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("[websockt消息 收到客户端发来的消息：{}]",message);
    }

    //使用广播的形式发送消息
    public void sendMessage(String message) {
        for (WebSocket webSocket : webSocketsSet) {
            log.info("[websockt消息] 广播消息 , message = {}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
