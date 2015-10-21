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

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.service.TableService;
import com.valeriisosliuk.service.UserService;

@Controller
public class GameController {

    private static final String START = "start";

    private static final Logger log = Logger.getLogger(GameController.class);

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private UserService userService;

    @Autowired
    private TableService tableService;

    @MessageMapping("/game")
    public void game(Message<Object> message, @Payload ActionDto dto) throws Exception {
        log.info("Got a message: " + dto);
        Principal principal = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);
        String currentUser = principal.getName();
        if (dto.getMessage().equals(START)) {
            tableService.tryStart(currentUser);
        } else {
            //TODO: Add turn handle
        }
    }

}
