package com.imooc.sell.Service.Impl;

import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/webSocket")

public class WebSocket {

    private Session session;

    @OnOpen
    public void onOpen(Session session) {

    }
}
