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
import com.valeriisosliuk.model.Rank;
import com.valeriisosliuk.model.Suit;
import com.valeriisosliuk.service.handler.TurnAdvisor;

@Component("pickActionHandler")
public class PickActionHandler implements ActionHandler {

    @Autowired
    private TurnAdvisor turnAdvisor;

    private static final Logger log = Logger.getLogger(PickActionHandler.class);

    @Override
    public State handleAction(Game game, Action action) {
        Player activePlayer = game.getActivePlayer();
        if (!activePlayer.getActiveState().isPickAllowed()) {
            log.warn("Pick not allowed");
            return game.getState();
            // TODO send message
        } else {
            activePlayer.pickCard(game.getCardHolder().pickCard());
            if (game.getState() == State.RESPOND_SUIT) {
                return processRespondSuitPick(game);
            } else {
                return processDefaultPick(game);
            }
        }
    }

    private State processDefaultPick(Game game) {
        Player activePlayer = game.getActivePlayer();
        Set<Card> turnOptions = turnAdvisor.getValidCardsForTurn(activePlayer.getHand(), game.getCardHolder().getLastCardInDiscard(),
                game.getState() == State.TURN_START);
        activePlayer.getActiveState().setTurnOptions(turnOptions);
        if (game.getCardHolder().getLastCardInDiscard().getRank() == Rank._6) {
            activePlayer.getActiveState().setPassAllowed(false);
            activePlayer.getActiveState().setPickAllowed(true);
        } else {
            activePlayer.getActiveState().setPassAllowed(true);
            activePlayer.getActiveState().setPickAllowed(false);
            if (turnOptions.isEmpty()) {
                return State.TURN_END;
            }
        }
        return game.getState();

    }

    private State processRespondSuitPick(Game game) {
        Player player = game.getActivePlayer();
        player.getActiveState().setPassAllowed(true);
        player.getActiveState().setPickAllowed(false);
        Suit demandedSuit = player.getActiveState().getDemandedSuit();
        Set<Card> turnOptions = turnAdvisor.getValidCardsForRespondSuit(player.getHand(), demandedSuit);
        if (turnOptions.isEmpty()) {
            player = game.getPlayerHolder().getNextActivePlayer();
            player.getActiveState().setDemandedSuit(demandedSuit);
            turnOptions = turnAdvisor.getValidCardsForRespondSuit(player.getHand(), demandedSuit);
        }
        player.getActiveState().setTurnOptions(turnOptions);
        return game.getState();
    }
}
