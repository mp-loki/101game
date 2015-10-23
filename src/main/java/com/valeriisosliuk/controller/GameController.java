package com.valeriisosliuk.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.StateDto;
import com.valeriisosliuk.model.Table;
import com.valeriisosliuk.service.TableService;
import com.valeriisosliuk.service.UserService;

@Controller
public class GameController {
    private static final String START = "start";
    private static final String PICK = "pick";

    private static final Logger log = Logger.getLogger(GameController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TableService tableService;

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/join")
    public String joinTable(Model model) {
        String currentPlayerName = userService.getCurrentUserName();
        Table table = tableService.joinTable(currentPlayerName);
        model.addAttribute("currentPlayer", currentPlayerName);
        if (table.isStarted()) {
            model.addAttribute("state", table.getPlayer(currentPlayerName));
            model.addAttribute("lastCard", table.getLastCardInDiscard());
        } else {
            model.addAttribute("message", "Waiting for players");

        }
        return "table";
    }

    @RequestMapping(value = "/")
    public String root(Model model) {
        return joinTable(model);
    }

    @RequestMapping(value = "/game/getState")
    public @ResponseBody StateDto getState(Model model) {
        String currentPlayerName = userService.getCurrentUserName();
        return tableService.getStateDto(currentPlayerName);
    }

    @RequestMapping(value = "/home")
    public String index(Model model) {
        return "index";
    }

    @MessageMapping("/game")
    public void game(Message<Object> message, @Payload ActionDto dto) throws Exception {
        log.info("Got a message: " + dto);
        String currentUser = getCurrentUserName(message);
        if (dto.getMessage().equals(START)) {
            tableService.tryStart(currentUser);
        } else if (dto.getMessage().equals(PICK)) {
            tableService.tryPickCard(currentUser);
        } else {
            // TODO: Add turn handle
            
        }
    }

    private String getCurrentUserName(Message<Object> message) {
        return message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class).getName();
    }
}
