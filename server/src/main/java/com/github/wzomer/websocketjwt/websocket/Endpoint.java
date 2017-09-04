package com.github.wzomer.websocketjwt.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@ServerEndpoint(value = "/websocket", configurator = SecurityConfigurator.class)
public class Endpoint {

    static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(final Session session) {
        System.out.println("Conectado com o usuário: " + session.getUserProperties().get("userId"));

        sessions.add(session);
    }

    @OnClose
    public void onClose(final Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(final Session session, final Throwable throwable) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(final String message, final Session session) {
        System.out.println(message);
    }
}
