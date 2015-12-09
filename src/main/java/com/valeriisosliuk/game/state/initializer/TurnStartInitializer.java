package com.valeriisosliuk.game.state.initializer;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.actionhandler.CardMoveHandler;
import com.valeriisosliuk.game.state.actionhandler.PickActionHandler;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.service.handler.TurnAdvisor;

@Component
public class TurnStartInitializer extends AbstractStateInitializer {

	@Autowired
	private TurnAdvisor turnAdvisor;
	
	@Autowired
	private CardMoveHandler cardMoveHandler;
	
	@Autowired 
	private PickActionHandler pickHandler;
	
	@Override
	public void initializeState(Game game) {
		Player activePlayer = game.getPlayerHolder().getActivePlayer();
		Set<Card> turnOptions = turnAdvisor.getValidCardsForTurn(activePlayer.getHand(), 
				game.getCardHolder().getLastCardInDiscard(), true);
		activePlayer.getActiveState().setTurnOptions(turnOptions);
	}
}
