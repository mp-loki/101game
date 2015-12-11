package com.valeriisosliuk.game.state.initializer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;

@Component("gameOverStateInitializer")
public class GameOverStateInitializer implements StateInitinalizer {
    
    private static final Logger log = Logger.getLogger(GameOverStateInitializer.class);
    @Override
    public void initializeState(Game game) {
        // TODO implement game over logic
        // Broadcast message to display winner and total points
        log.info("Game Over");
    }

}
