package com.toggersapi.ToggersAPICore.Controller;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        // Lógica para lidar com mensagens recebidas do cliente
        String payload = message.getPayload();

        // Aqui você pode processar a mensagem recebida e responder, se necessário
        session.sendMessage(new TextMessage("Received: " + payload));
    }

}
