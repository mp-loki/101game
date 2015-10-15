package com.valeriisosliuk.controller;

import java.security.Principal;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.userdetails.User;

import com.valeriisosliuk.model.GameTurnMessage;
import com.valeriisosliuk.service.UserService;

@Controller
public class MessageController {

    private static final Logger log = Logger.getLogger(MessageController.class);

    private SimpMessagingTemplate template;
    private UserService userService;

    @Autowired
    public MessageController(SimpMessagingTemplate template, UserService userService) {
        this.template = template;
        this.userService = userService;
    }

    @MessageMapping("/gameQueue")
    public void greeting(Message<Object> message, @Payload GameTurnMessage turnMessage) throws Exception {
        log.info("Got a message: " + turnMessage);
        Principal principal = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);
        String authedSender = principal.getName();
        String nextUser = getNextUserName(authedSender);  
        
        GameTurnMessage reply = new GameTurnMessage();
        reply.setPlayer("System");
        reply.setMessage("User '" + authedSender + "' Said:  '" + turnMessage.getMessage() + "'");
        template.convertAndSendToUser(nextUser, "/queue/messages", reply);
        template.convertAndSendToUser(authedSender, "/queue/messages", reply);
    }
    
    private String getNextUserName(String currentUserName) {
        for (String userName : userService.getLoggedInUserNames()) {
            if (!currentUserName.equals(userName)) {
                return userName;
            }
        }
        //Return same user if no one else is logged in
        return currentUserName;
    }

}
