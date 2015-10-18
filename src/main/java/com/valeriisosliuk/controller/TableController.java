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

import com.valeriisosliuk.Table;
import com.valeriisosliuk.model.GameTurnMessage;
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
		Principal principal = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);
        String currentUserName = principal.getName();
		Table table = tableService.getCurrentTableForUser(currentUserName);
		boolean started = table.start(currentUserName);
        GameTurnMessage reply = new GameTurnMessage();

		if (!started) {
			reply.setMessage("Waiting for other users");
		} else {
			reply.setMessage("Game started");
			reply.setCards(table.getPlayersHandCards(currentUserName));
		}
		template.convertAndSendToUser(currentUserName, "/queue/messages", reply);
	}
}
