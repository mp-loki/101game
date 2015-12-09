package com.valeriisosliuk.game.state.actionhandler;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.service.handler.TurnAdvisor;

@Component
public class RespondSuitPickHandler implements ActionHandler {

    @Autowired
    private TurnAdvisor turnAdvisor;

    private static final Logger log = Logger.getLogger(RespondSuitPickHandler.class);

    @Override
    public State handleAction(Game game, Action action) {
        Player activePlayer = game.getActivePlayer();
        if (!activePlayer.getActiveState().isPickAllowed()) {
            log.warn("Pick not allowed");
            // TODO send message to user
        } else {
            activePlayer.pickCard(game.getCardHolder().pickCard());
            activePlayer.getActiveState().setPassAllowed(true);
            activePlayer.getActiveState().setPickAllowed(false);
            Set<Card> turnOptions = turnAdvisor.getValidCardsForRespondSuit(activePlayer.getHand(), activePlayer.getActiveState().getDemandedSuit());
            activePlayer.getActiveState().setTurnOptions(turnOptions);
            if (turnOptions.isEmpty()) {
                return State.RESPOND_SUIT_END;
            }
        }
        return game.getState();
    }
}
