package com.valeriisosliuk.game.state.processor;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.actionhandler.CardMoveHandler;
import com.valeriisosliuk.game.state.actionhandler.PickActionHandler;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.service.handler.PickHandler;
import com.valeriisosliuk.service.handler.TurnAdvisor;

@Component
public class TurnStartProcessor extends AbstractStateProcessor {

	@Autowired
	private TurnAdvisor turnAdvisor;
	
	@Autowired
	private CardMoveHandler cardMoveHandler;
	
	@Autowired 
	private PickActionHandler pickHandler;
	
	@Override
	public void initializeState(Game game) {
		Player activePlayer = game.getActivePlayer();
		Set<Card> turnOptions = turnAdvisor.getValidCardsForTurn(activePlayer.getHand(), 
				game.getCardHolder().getLastCardInDiscard(), true);
		activePlayer.getActiveState().setTurnOptions(turnOptions);
	}

	@Override
	public void applyAction(Game game, Action action) {
		if (validate(game, action)) {
			switch (action.getType()) {
				case MOVE : game.setState(cardMoveHandler.handleAction(game, action));
				case PICK : game.setState(pickHandler.handleAction(game, action));
				default : //won't apply after validation;
			}
		}
	}

	@Override
	protected boolean validateAction(Action action) {
		ActionType actionType = action.getType();
		return actionType == ActionType.MOVE || actionType == ActionType.PICK;
	}
}
