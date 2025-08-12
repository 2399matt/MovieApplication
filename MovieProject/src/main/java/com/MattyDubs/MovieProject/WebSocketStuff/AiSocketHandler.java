package com.MattyDubs.MovieProject.WebSocketStuff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AiSocketHandler extends TextWebSocketHandler {

    private final Map<String, ConcurrentWebSocketSessionDecorator> sessions = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;

    private final ChatClient chatClient;

    @Autowired
    public AiSocketHandler(ObjectMapper objectMapper, ChatClient.Builder chatClient) {
        this.objectMapper = objectMapper;
        this.chatClient = chatClient.build();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        ConcurrentWebSocketSessionDecorator safeSession = new ConcurrentWebSocketSessionDecorator(session, 10000, 1024 * 1024);
        String username = session.getPrincipal().getName();
        sessions.put(username, safeSession);
        System.out.println("Opened session for: " + username);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = session.getPrincipal().getName();
        sessions.remove(username);
        System.out.println("Closed session for: " + username);
    }


    // delegate some logic to other methods, this got boggled down with too many exception cases.
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        System.out.println(chatMessage.getMessage());
        validateSessionSender(session, chatMessage);
        CompletableFuture
                .supplyAsync(() -> getBotResponse(chatMessage.getMessage()))
                .thenAccept((response) -> sendMessage(session, response));
    }

    private String getBotResponse(String message) {
        try {
            return chatClient.prompt(message).call().content();
        } catch (Exception e) {
            System.out.println("Ollama error: " + e.getMessage());
            throw new CompletionException(e);
        }
    }

    private void sendMessage(WebSocketSession session, String response) {
        try {
            String jsonAi = objectMapper.writeValueAsString(new ChatMessage("TARS", response));
            ConcurrentWebSocketSessionDecorator safeSession = sessions.get(session.getPrincipal().getName());
            if (safeSession != null && safeSession.isOpen()) {
                safeSession.sendMessage(new TextMessage(jsonAi));
            }
        } catch (Exception e) {
            if (e instanceof JsonProcessingException) {
                System.out.println("error processing ChatMessage: " + e.getMessage());
            } else if (e instanceof IOException) {
                System.out.println("error sending WS message" + e.getMessage());
            } else {
                System.out.println("Holy cwap. Unexpected WS error: " + e.getMessage());
            }
        }
    }

    private void validateSessionSender(WebSocketSession session, ChatMessage chatMessage) {
        if (!chatMessage.getSender().equals(session.getPrincipal().getName()) && !chatMessage.getSender().equals("System")) {
            try {
                session.close(CloseStatus.NOT_ACCEPTABLE);
            } catch (IOException e) {
                throw new RuntimeException("Issue closing session: " + e.getMessage());
            }
            throw new RuntimeException("SEC: Sender mismatch for WS session");
        }
    }
}

