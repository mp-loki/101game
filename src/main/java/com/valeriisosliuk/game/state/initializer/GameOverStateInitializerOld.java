package com.valeriisosliuk.game.state.initializer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.service.GameService;

@Component(value="gameOverStateInitializerZaloopa")
public class GameOverStateInitializerOld implements StateInitinalizer {
    
    @Autowired
    private GameService gameService;
    
    private static final Logger log = Logger.getLogger(GameOverStateInitializerOld.class);
    @Override
    public void initializeState(Game game) {
        // TODO Broadcast message to display winner and total points
    	gameService.dismissGame(game);
        log.info("Game Over");
    }

}
