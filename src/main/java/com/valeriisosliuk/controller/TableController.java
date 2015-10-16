package com.valeriisosliuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.valeriisosliuk.Table;
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
		String currentUserName = userService.getCurrentUserName();
		Table table = tableService.joinFirstAvailableTable(currentUserName);
		boolean started = table.start();
		if (!started) {
			//TODO: Send message "Waiting for other users"
		} else {
			//TODO: send cards and start game 
		}
		
	}
}
