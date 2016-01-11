package com.valeriisosliuk.game.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageService {
	
	private static final Logger log = Logger.getLogger(MessageService.class);
    
	@PostConstruct
	public void init() {
	    ServiceLocator.setMessageService(this);
	}
	
    @Autowired
    private SimpMessagingTemplate template;

    public <T> void send(String player, T t) {
    	log.info("Sending unicast message to " + player +": " + t);
        template.convertAndSendToUser(player, "/queue/messages", t);
    }

	public <T> void sendToAll(List<String> players, T t) {
		log.info("Sending broadcast message: " + t);
		players.stream().forEach(p -> send(p,t));
	}
}
