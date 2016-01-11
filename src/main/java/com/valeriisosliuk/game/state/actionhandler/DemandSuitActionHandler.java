package com.valeriisosliuk.game.state.actionhandler;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Suit;
import com.valeriisosliuk.game.state.State;

@Component("demandSuitActionHandler")
public class DemandSuitActionHandler implements ActionHandler {

    @Override
    public State handleAction(Game game, Action action) {
        Suit demandedSuit = action.getDemandedSuit();
        game.getPlayerHolder().getNextActivePlayer().getActiveState().setDemandedSuit(demandedSuit);
        return State.RESPOND_SUIT;
    }

}
