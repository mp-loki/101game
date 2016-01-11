package com.valeriisosliuk.game.state.actionhandler;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.Card;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.model.Rank;
import com.valeriisosliuk.game.model.Suit;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.turnadvisor.TurnAdvisor;

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
        Player player = game.getActivePlayer();
        Set<Card> turnOptions = turnAdvisor.getValidCardsForTurn(player.getHand(), game.getCardHolder().getLastCardInDiscard(),
                game.getState() == State.TURN_START);
        boolean pickAllowed;
        boolean passAllowed;
        State result = game.getState();
        if (game.getCardHolder().getLastCardInDiscard().getRank() == Rank._6) {
            pickAllowed = true;
            passAllowed = false;
        } else {
            pickAllowed = false;
            passAllowed = true;
            if (turnOptions.isEmpty()) {
                result =  State.TURN_END;
            }
        }
        player.getActiveState().update(pickAllowed, passAllowed, turnOptions);
        return result;

    }

    private State processRespondSuitPick(Game game) {
        Player player = game.getActivePlayer();
        Suit demandedSuit = player.getActiveState().getDemandedSuit();
        Set<Card> turnOptions = turnAdvisor.getValidCardsForRespondSuit(player.getHand(), demandedSuit);
        if (turnOptions.isEmpty()) {
            Player nextPlayer = player = game.getPlayerHolder().getNextActivePlayer();
            nextPlayer.getActiveState().setDemandedSuit(demandedSuit);
            turnOptions = turnAdvisor.getValidCardsForRespondSuit(player.getHand(), demandedSuit);
            nextPlayer.getActiveState().update(true, false, turnOptions);
        } else {
        	player.getActiveState().update(false, true, turnOptions);
        }
        return game.getState();
    }
}
