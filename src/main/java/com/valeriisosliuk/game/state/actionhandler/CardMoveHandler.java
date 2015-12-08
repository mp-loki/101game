package com.valeriisosliuk.game.state.actionhandler;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;
import static com.valeriisosliuk.game.state.State.*;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Rank;
import com.valeriisosliuk.service.handler.TurnAdvisor;

@Component
public class CardMoveHandler implements ActionHandler {

	private static final Logger log = Logger.getLogger(CardMoveHandler.class);

	@Autowired
	private TurnAdvisor turnAdvisor;

	@Override
	public State handleAction(Game game, Action action) {
		if (!validateMove(game, action)) {
			return game.getState();
		}
		Player activePlayer = game.getActivePlayer();
		Card card = action.getCard();

		if (activePlayer.removeCard(card)) {
			game.getCardHolder().putCardInDiscard(card);
			activePlayer.getActiveState().addCurrentTurnCard(card);
			Set<Card> nextTurnOptions = getTurnOptions(game, false);
			activePlayer.getActiveState().setTurnOptions(nextTurnOptions);
			if (card.getRank() == Rank._6) {
				return processSixCardMove(game, card);
			} else {
				return processCardMove(game, card);
			}
		} else {
			log.warn("Unable to remove " + action.getCard() + " fron hand " + activePlayer.getHand());
			return game.getState();
		}
	}

	private State processSixCardMove(Game game, Card card) {
		Player activePlayer = game.getActivePlayer();
		activePlayer.getActiveState().setPickAllowed(true);
		activePlayer.getActiveState().setPassAllowed(false);
		return TURN_IN_PROGRESS;
	}

	private State processCardMove(Game game, Card card) {
		Player activePlayer = game.getActivePlayer();
		if (CollectionUtils.isEmpty(activePlayer.getActiveState().getTurnOptions())) {
			return TURN_END;
		} else {
			activePlayer.getActiveState().setPassAllowed(true);
			return TURN_IN_PROGRESS;
		}
	}

	private Set<Card> getTurnOptions(Game game, boolean firstMove) {
		return turnAdvisor.getValidCardsForTurn(game.getActivePlayer().getHand(), game.getCardHolder()
				.getLastCardInDiscard(), firstMove);
	}

	private boolean validateMove(Game game, Action action) {
		Card card = action.getCard();
		Set<Card> turnOptions = game.getActivePlayer().getActiveState().getTurnOptions();
		if (turnOptions == null) {
			turnOptions = getTurnOptions(game, game.getState() == TURN_START);
		}
		if (turnOptions != null && turnOptions.contains(card)) {
			return true;
		} else {
			log.warn("Card Move " + action.getCard() + " over " + game.getCardHolder().getLastCardInDiscard()
					+ " is not valid");
			return false;
		}
	}
}