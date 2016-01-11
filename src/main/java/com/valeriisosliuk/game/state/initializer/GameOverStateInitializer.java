package com.valeriisosliuk.game.state.initializer;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.GameOverDto;
import com.valeriisosliuk.game.dto.GameOverPlayerInfoDto;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.service.GameService;
import com.valeriisosliuk.game.service.MessageService;

@Component(value = "gameOverStateInitializer")
public class GameOverStateInitializer implements StateInitinalizer {

    private static final Logger log = Logger.getLogger(GameOverStateInitializer.class);

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private GameService gameService;

    @Override
    public void initializeState(Game game) {
        gameService.dismissGame(game);
        GameOverDto dto = getGameOverDto(game);
        log.info("GAME OVER" + dto);
        messageService.sendToAll(game.getPlayerNames(), getGameOverDto(game));
    }

    private GameOverDto getGameOverDto(Game game) {
        List<GameOverPlayerInfoDto> players = game.getPlayers().stream().map(p -> new GameOverPlayerInfoDto(p.getName(), p.getTotalPoints()))
                .collect(Collectors.toList());
        return new GameOverDto(players, game.getWinner());
    }

}
