package com.valeriisosliuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.InfoDto;
import com.valeriisosliuk.dto.ReplyDto;

@Component
public class MessageService {
    
    @Autowired
    private SimpMessagingTemplate template;

    public void send(String player, ReplyDto dto) {
        template.convertAndSendToUser(player, "/queue/messages", dto);
    }
    
    public void sendToAll(InfoDto dto) {
        template.convertAndSend("/topic/message", dto);
    }
    
}
