package com.valeriisosliuk.game.service;

import java.util.List;
import java.util.Optional;

import com.valeriisosliuk.game.dto.PlayerInfoDto;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Player;

public interface GameService {
    
    void dismissGame(Game game);

    List<PlayerInfoDto> getMatesInfo(Player player, Game game);

    List<String> getGameMates(String playerName);

    Optional<Game> getGameInstance(String playerName);

    Game getGame(String playerName);
}
