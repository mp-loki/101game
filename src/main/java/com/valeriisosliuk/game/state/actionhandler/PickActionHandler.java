package com.valeriisosliuk.game.state.actionhandler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.Rank;
import com.valeriisosliuk.service.handler.TurnAdvisor;

@Component
public class PickActionHandler implements ActionHandler {
	
	@Autowired
	private TurnAdvisor turnAdvisor;
	
	private static final Logger log = Logger.getLogger(PickActionHandler.class);

	@Override
	public State handleAction(Game game, Action action) {
		Player activePlayer = game.getActivePlayer();
		if (!activePlayer.getActiveState().isPickAllowed()) {
			log.warn("Pick not allowed");
			//TODO send message
		} else {
			activePlayer.pickCard(game.getCardHolder().pickCard());
			activePlayer.getActiveState().setTurnOptions(turnAdvisor.getValidCardsForTurn(activePlayer.getHand(),
					game.getCardHolder().getLastCardInDiscard(), game.getState() == State.TURN_START));
			if (game.getCardHolder().getLastCardInDiscard().getRank() == Rank._6) {
				activePlayer.getActiveState().setPassAllowed(false);
				activePlayer.getActiveState().setPickAllowed(true);
			} else {
				activePlayer.getActiveState().setPassAllowed(true);
				activePlayer.getActiveState().setPickAllowed(false);
			}
		}
		return game.getState();
	}
}
