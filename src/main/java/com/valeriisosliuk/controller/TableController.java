package com.valeriisosliuk.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Table;
import com.valeriisosliuk.service.TableService;
import com.valeriisosliuk.service.UserService;

@Controller
public class TableController {
    
	@Autowired
	private UserService userService;
    
	@Autowired
	private TableService tableService;

	@Autowired
	private SimpMessagingTemplate template;

	@RequestMapping(value = "/join")
	public String joinTable(
			@RequestParam(value = "tableId", required = false, defaultValue = "1") Integer tableId,
			Model model) {
		String currentUserName = userService.getCurrentUserName();
		Table table = tableService.joinTable(currentUserName, tableId);
		model.addAttribute("tableId", tableId);
		model.addAttribute("players", table.getPlayers());
		model.addAttribute("message", "Waiting for players");
		return "table";
	}
	
	@MessageMapping("/start")
    public void startTable(Message<Object> message) {
	    String currentUserName = getCurrentUserName(message);
	    tableService.tryStart(currentUserName);
	    
	}
	/*
	@MessageMapping("/start")
	public void startTable(Message<Object> message) {
        String currentUserName = getCurrentUserName(message);
		Table table = tableService.getCurrentTableForUser(currentUserName);
		boolean started = table.start(currentUserName);
		
		if (!started) {
		    sendWaitMessage(currentUserName);
		} else {
		    sendGameStartedMessages(table);
		}
		
        GameMessage reply = new GameMessage();

		if (!started) {
			reply.setMessage("Waiting for other users");
		} else {
			reply.setMessage("Game started");
			reply.setHand(table.getPlayersHandCards(currentUserName));
		}
		template.convertAndSendToUser(currentUserName, "/queue/messages", reply);
	}
    private void sendWaitMessage(String currentUserName) {
        GameMessage reply = new GameMessage();
        reply.setMessage("Waiting for other users");
        template.convertAndSendToUser(currentUserName, "/queue/messages", reply);
        
    }
	private void sendGameStartedMessages(Table table) {
        for (String player : table.getPlayers()) {
            GameMessage reply = tableService.getGameMessage(table, player);
            template.convertAndSendToUser(player, "/queue/messages", reply);
        }
        
    }
	*/
	private String getCurrentUserName(Message<Object> message) {
	    return  message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class).getName();
	}
}
