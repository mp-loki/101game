package com.valeriisosliuk.game.state.actionhandler;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;

@Component("quitActionHandler")
public class QuitActionHandler implements ActionHandler {
    
    private static final Logger log = Logger.getLogger(QuitActionHandler.class);
    
    @Override
    public State handleAction(Game game, Action action) {
        log.info("User " + action.getPlayerName() + " quits game!");
        /*
        if (game.getState() == State.INITIAL) {
            game.getPlayerHolder().quit(action.getPlayerName());
            return State.INITIAL;
        }
        */
        return State.GAME_OVER;
    }

}
