package com.valeriisosliuk.game.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ActiveStateDto;
import com.valeriisosliuk.dto.InactiveStateDto;
import com.valeriisosliuk.dto.InitialStateDto;
import com.valeriisosliuk.dto.StateDto;
import com.valeriisosliuk.game.Game;

@Component
public class ResponseService {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    public StateDto getStateDto(String playerName) {
        Optional<Game> gameOpt = gameService.getGameInstance(playerName);
        if (gameOpt.equals(Optional.empty())) {
            return crateInitialStateDto();
        } else {
            Game game = gameOpt.get();
            if (game.getActivePlayer().getName().equals(playerName)) {
                return getActiveStateDto(game);
            } else {
                return getInactiveStateDto(game);
            }

        }
    }

    private StateDto getInactiveStateDto(Game game) {
        return new InactiveStateDto();
    }

    private StateDto getActiveStateDto(Game game) {
        return new ActiveStateDto();
    }

    private StateDto crateInitialStateDto() {
        InitialStateDto dto = new InitialStateDto(userService.getLoggedInUsers());
        return dto;
    }

}
