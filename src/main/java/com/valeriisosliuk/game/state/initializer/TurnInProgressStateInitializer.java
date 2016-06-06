package com.valeriisosliuk.game.state.initializer;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Rank;

@Component("turnInProgressStateInitializer")
public class TurnInProgressStateInitializer implements StateInitinalizer {

    @Override
    public void initializeState(Game game) {
        if (game.getCardHolder().getLastCardInDiscard().getRank() == Rank._6) {
            game.getActivePlayer().getActiveState().setPassAllowed(false);
        }
    }

}
