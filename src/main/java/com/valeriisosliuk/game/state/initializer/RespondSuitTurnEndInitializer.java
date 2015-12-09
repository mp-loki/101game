package com.valeriisosliuk.game.state.initializer;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.Suit;

@Component
public class RespondSuitTurnEndInitializer implements StateInitinalizer {

    @Override
    public void initializeState(Game game) {
        Suit demand = game.getActivePlayer().getActiveState().getDemandedSuit();
        game.getPlayerHolder().getNextActivePlayer().getActiveState().setDemandedSuit(demand);
        game.setState(State.RESPOND_SUIT);
    }
}
