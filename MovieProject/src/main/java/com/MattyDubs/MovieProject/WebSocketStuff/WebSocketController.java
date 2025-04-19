package com.MattyDubs.MovieProject.WebSocketStuff;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.net.http.HttpClient;
import java.security.Principal;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatClient chatClient;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate, ChatClient.Builder chatClient) {
        this.messagingTemplate = messagingTemplate;
        this.chatClient = chatClient.build();
    }

    @MessageMapping("/welcome")
    public void sendAiWelcomeResponse(@Payload ChatMessage chatMessage, Principal principal) {
        System.out.println(chatMessage.getMessage());
        String prompt = chatMessage.getMessage();
        String response = chatClient.prompt(prompt).call().content();
        if (response != null) {
            ChatMessage aiResponse = new ChatMessage("TARS", response.trim());
            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/ai", aiResponse);
        } else {
            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/ai", "Oops. TARS dead.");
        }
    }

    @MessageMapping("/chat")
    public void sendAiChatResponse(@Payload ChatMessage chatMessage, Principal principal) {
        if (!chatMessage.getSender().equals(principal.getName())) {
            throw new SecurityException("Unauthorized WS Request.");
        }
        String prompt = chatMessage.getMessage();
        String response = chatClient.prompt(prompt).call().content();
        if (response != null) {
            ChatMessage aiResponse = new ChatMessage("TARS", response.trim());
            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/ai", aiResponse);
        } else {
            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/ai", "Oops.");
        }
    }

    //FOR MANUAL SUBSCRIPTION (solution without spring's /user prefix, maybe grab the session?).

//    @MessageMapping("/welcome")
//    public void sendAiResponse(@Payload ChatMessage chatMessage, Principal principal){
//        System.out.println(chatMessage.getMessage());
//        String prompt = chatMessage.getMessage();
//        String response = chatClient.prompt(prompt).call().content();
//        if(response != null){
//            FOR MANUAL SUBSCRIPTION
//            ChatMessage aiResponse = new ChatMessage("BigBoyBiggie", response);
//            messagingTemplate.convertAndSend("/queue/ai/"+principal.getName(), aiResponse);
//        }
//    }
}
