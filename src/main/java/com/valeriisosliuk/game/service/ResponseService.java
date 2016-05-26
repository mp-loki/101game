package com.valeriisosliuk.game.service;

import com.valeriisosliuk.game.dto.Dto;
import com.valeriisosliuk.game.dto.GameStateDto;
import com.valeriisosliuk.game.model.Game;

public interface ResponseService {

    Dto getStateDto(String playerName);

    GameStateDto getGameStateDto(String playerName, Game game);

}
