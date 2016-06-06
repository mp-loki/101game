package com.valeriisosliuk.game.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceImpl implements MessageService {
	
	private static final Logger log = Logger.getLogger(MessageServiceImpl.class);
	
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
