package com.valeriisosliuk.game.state.actionhandler;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
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

@Component("respondSuitActionHandler")
public class RespondSuitActionHandler implements ActionHandler {

    private static final Logger log = Logger.getLogger(RespondSuitActionHandler.class);

    @Autowired
    private TurnAdvisor turnAdvisor;

    @Override
    public State handleAction(Game game, Action action) {
        Card card = action.getCard();
        Suit demanded = game.getActivePlayer().getActiveState().getDemandedSuit();
        if (card.getRank() == Rank.JACK) {
            return processJackCardMove(game, card);
        }
        if (card.getSuit() == demanded) {
            return processRespondCardMove(game, card);
        }
        log.warn("The move with card " + card + " is not valid. Demanded suite is: " + demanded);
        return game.getState();
    }

    private State processJackCardMove(Game game, Card card) {
        processCard(game, card);
        return CollectionUtils.isEmpty(game.getActivePlayer().getActiveState().getTurnOptions()) ? State.DEMAND_SUIT : State.TURN_IN_PROGRESS;

    }

    private State processRespondCardMove(Game game, Card card) {
        processCard(game, card);
        return CollectionUtils.isEmpty(game.getActivePlayer().getActiveState().getTurnOptions()) && card.getRank() != Rank._6 ? State.TURN_END : State.TURN_IN_PROGRESS;
    }

    private void processCard(Game game, Card card) {
        Player player = game.getActivePlayer();
        player.removeCard(card);
        player.getActiveState().addCurrentTurnCard(card);
        game.putCardInDiscard(card);
        Set<Card> nextTurnOptions = turnAdvisor.getValidCardsForTurn(game.getActivePlayer().getHand(), game.getCardHolder().getLastCardInDiscard(), false);
        game.getActivePlayer().getActiveState().setTurnOptions(nextTurnOptions);
    }

}
