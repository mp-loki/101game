package com.valeriisosliuk.game.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;

@Component
public class MessageService {
	
	private static final Logger log = Logger.getLogger(MessageService.class);
    
	@PostConstruct
	public void init() {
	    MessageServiceLocator.setMessageService(this);
	}
	
    @Autowired
    private SimpMessagingTemplate template;

    public <T> void send(String player, T t) {
    	log.info("Sending unicast message: " + t);
        template.convertAndSendToUser(player, "/queue/messages", t);
    }
    
    @Deprecated
    public <T> void sendToAll(T t) {
        template.convertAndSend("/topic/message", t);
    }
    @Deprecated
    public void processActionResult(ActionResult actionResult) {
    	for (BroadcastDto stateDto : actionResult.getGeneralUpdates()) {
    		sendToAll(stateDto);
    	}
    	Map<String, ResponseDto> playerUpdates = actionResult.getPlayerUpdates();
    	for (String player : playerUpdates.keySet()) {
    		send(player, playerUpdates.get(player));
    	}
    }

	public <T> void sendToAll(List<String> players, T t) {
		log.info("Sending broadcast message: " + t);
		players.stream().forEach(p -> send(p,t));
	}
}
