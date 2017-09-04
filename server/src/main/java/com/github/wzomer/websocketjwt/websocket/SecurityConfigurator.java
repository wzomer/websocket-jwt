package com.github.wzomer.websocketjwt.websocket;

import com.github.wzomer.websocketjwt.auth.TokenService;

import javax.inject.Inject;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

public class SecurityConfigurator extends ServerEndpointConfig.Configurator {

    @Inject
    private TokenService tokenService;

    @Override
    public void modifyHandshake(final ServerEndpointConfig sec, final HandshakeRequest request, final HandshakeResponse response) {
        final Map<String, List<String>> headers = request.getHeaders();

        final String authorization = headers.get(TokenService.HEADER_NAME).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("É necessário enviar o token de autorização"));

        final String userId = tokenService.getSubject(authorization);
        sec.getUserProperties().put("userId", userId);

        super.modifyHandshake(sec, request, response);
    }
}
