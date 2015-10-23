package com.valeriisosliuk.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;

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
    
    public void processActionResult(ActionResult actionResult) {
    	for (ResponseDto stateDto : actionResult.getGeneralUpdates()) {
    		sendToAll(stateDto);
    	}
    	Map<String, ResponseDto> playerUpdates = actionResult.getPlayerUpdates();
    	for (String player : playerUpdates.keySet()) {
    		send(player, playerUpdates.get(player));
    	}
    }
}
