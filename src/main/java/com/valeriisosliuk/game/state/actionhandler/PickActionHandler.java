package com.valeriisosliuk.game.state.actionhandler;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;

@Component
public class PickActionHandler implements ActionHandler {

	@Override
	public State handleAction(Game game, Action action) {
		Player activePlayer = game.getActivePlayer();
		activePlayer.pickCard(game.getCardHolder().pickCard());
		return State.TURN_IN_PROGRESS;
	}
}
