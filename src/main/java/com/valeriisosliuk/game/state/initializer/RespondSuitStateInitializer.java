package com.valeriisosliuk.game.state.initializer;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Suit;
import com.valeriisosliuk.service.handler.TurnAdvisor;

@Component("respondSuitStateInitializer")
public class RespondSuitStateInitializer implements StateInitinalizer {
    
    @Autowired
    private TurnAdvisor turnAdvisor;
    
    @Override
    public void initializeState(Game game) {
        Player activePlayer = game.getActivePlayer();
        Suit demand = activePlayer.getActiveState().getDemandedSuit();
        Set<Card> validTurns = turnAdvisor.getValidCardsForRespondSuit(activePlayer.getHand(), demand);
        game.getActivePlayer().getActiveState().setTurnOptions(validTurns);
    }

}
