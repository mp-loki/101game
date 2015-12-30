package com.valeriisosliuk.controller;

import java.security.Principal;
import java.util.List;

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

import com.valeriisosliuk.dto.Dto;
import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.dto.GameStateDto;
import com.valeriisosliuk.dto.OnlineUserDto;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.service.ActionService;
import com.valeriisosliuk.game.service.GameService;
import com.valeriisosliuk.game.service.ResponseService;
import com.valeriisosliuk.game.service.UserService;
import com.valeriisosliuk.model.Table;
import com.valeriisosliuk.service.TableService;

@Controller
public class GameController {

    private static final Logger log = Logger.getLogger(GameController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TableService tableService;
    
    @Autowired
    private ResponseService responseService;
    
    @Autowired
    private ActionService actionService;
    
    @Autowired 
    GameService gameService;
 
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
        return home(model);
    }

    @RequestMapping(value = "/game/getState")
    public @ResponseBody Dto getState(Model model) {
        String currentPlayerName = userService.getCurrentUserName();
        return responseService.getStateDto(currentPlayerName);
    }
    
    @RequestMapping(value = "/game/getUsers")
    public @ResponseBody List<OnlineUserDto> getLoggedInUsers(Model model) {
        return userService.getLoggedInUsers();
    }

    @RequestMapping(value = "/home")
    public String home(Model model) {
        String currentPlayerName = userService.getCurrentUserName();
        model.addAttribute("currentPlayer", currentPlayerName);
        return "home";
    }

    @MessageMapping("/game")
    public void game(Message<Object> message, @Payload Action dto) throws Exception {
        log.info("Got a message: " + dto);
        String currentUser = getCurrentUserName(message);
        dto.setCurrentPlayer(currentUser);
        Game game = gameService.getGame(currentUser);
        actionService.handleAction(game, dto);
    }

    private String getCurrentUserName(Message<Object> message) {
        return message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class).getName();
    }
}
