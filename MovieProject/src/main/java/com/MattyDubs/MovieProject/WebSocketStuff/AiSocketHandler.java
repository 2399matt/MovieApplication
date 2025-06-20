package com.MattyDubs.MovieProject.WebSocketStuff;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AiSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;

    private final ChatClient chatClient;

    @Autowired
    public AiSocketHandler(ObjectMapper objectMapper, ChatClient.Builder chatClient) {
        this.objectMapper = objectMapper;
        this.chatClient = chatClient.build();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = session.getPrincipal().getName();
        sessions.put(username, session);
        System.out.println("Opened session for: " + username);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = session.getPrincipal().getName();
        sessions.remove(username);
        System.out.println("Closed session for: " + username);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        System.out.println(chatMessage.getMessage());
        if (!chatMessage.getSender().equals(session.getPrincipal().getName()) && !chatMessage.getSender().equals("System")) {
            throw new SecurityException("Invalid Session destination");
        }
        String response = chatClient.prompt(chatMessage.getMessage()).call().content();
        String jsonAi = objectMapper.writeValueAsString(new ChatMessage("TARS", response));
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(jsonAi));
        }
    }
}
