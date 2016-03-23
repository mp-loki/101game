package com.valeriisosliuk.game.state.actionhandler;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.model.Suit;
import com.valeriisosliuk.game.state.State;

@Component("turnEndActionHandler")
public class TurnEndActionHandler implements ActionHandler {

    private static final Logger log = Logger.getLogger(TurnEndActionHandler.class);
    
    @Override
    public State handleAction(Game game, Action action) {
        Player activePlayer = game.getActivePlayer();
        if (activePlayer.getActiveState().isPassAllowed()) {
            return processTurnEnd(game, action);
        } else {
            log.warn("Pass is not allowed. Pick a card");
            return game.getState();
        }
    }

    private State processTurnEnd(Game game, Action action) {
        if (game.getState() == State.RESPOND_SUIT) {
            Suit demand = game.getActivePlayer().getActiveState().getDemandedSuit();
            game.getPlayerHolder().getNextActivePlayer().getActiveState().setDemandedSuit(demand);
            return State.RESPOND_SUIT;
        } else {
            return State.TURN_END;
        }
    }
}