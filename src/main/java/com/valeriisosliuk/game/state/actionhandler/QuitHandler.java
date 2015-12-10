package com.valeriisosliuk.game.state.actionhandler;

import org.apache.log4j.Logger;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;

public class QuitHandler implements ActionHandler {
    
    private static final Logger log = Logger.getLogger(QuitHandler.class);
    
    @Override
    public State handleAction(Game game, Action action) {
        log.info("User " + action.getPlayer() + " quits game!");
        return State.GAME_OVER;
    }

}
