package com.valeriisosliuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageService {
    
    @Autowired
    private SimpMessagingTemplate template;

    public <T> void send(String player, T t) {
        template.convertAndSendToUser(player, "/queue/messages", t);
    }
    
    public <T> void sendToAll(T t) {
        template.convertAndSend("/topic/message", t);
    }
    
}
