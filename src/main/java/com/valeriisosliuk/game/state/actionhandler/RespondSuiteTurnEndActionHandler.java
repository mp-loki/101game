package com.valeriisosliuk.game.state.actionhandler;

import org.apache.log4j.Logger;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;

public class RespondSuiteTurnEndActionHandler implements ActionHandler {

    private static final Logger log = Logger.getLogger(RespondSuiteTurnEndActionHandler.class);
    
    @Override
    public State handleAction(Game game, Action action) {
        Player activePlayer = game.getActivePlayer();
        if (activePlayer.getActiveState().isPassAllowed()) {
            return State.RESPOND_SUIT_END;
        } else {
            //TODO send pick card message
            log.warn("Pass is not allowed. Pick a card");
            return game.getState();
        }
    }
}
