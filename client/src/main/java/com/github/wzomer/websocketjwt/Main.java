package com.github.wzomer.websocketjwt;

import com.squareup.okhttp.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;

public class Main {

    public static void main(String[] args) throws Exception {
        WebSocketClient client = new WebSocketClient();
        client.start();
        ClientEndpoint socket = new ClientEndpoint();
        URI destUri = new URI("ws://localhost:8080/websocket");
        ClientUpgradeRequest request = new ClientUpgradeRequest();
        request.setHeader("Authorization", getAuthorization());
        client.connect(socket, destUri, request).get();
        client.stop();
    }

    private static String getAuthorization() throws IOException {
        final OkHttpClient httpClient = new OkHttpClient();

        final String json = "{\"userId\": \"wellington\", \"password\": \"senhadificil\"}";

        Request authRequest = new Request.Builder()
                .url("http://localhost:8080/auth")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                .build();

        final Response response = httpClient.newCall(authRequest).execute();
        return "Bearer " + response.body().string();
    }

    @WebSocket
    public static class ClientEndpoint {

        @OnWebSocketConnect
        public void onConnect(final Session session) {
            System.out.println("Conectado");
        }
    }
}
