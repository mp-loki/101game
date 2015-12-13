package com.valeriisosliuk.game.state.initializer;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;

public abstract class AbstractStateInitializer implements StateInitinalizer {
	
	protected boolean validatePlayer(Game game, Action action) {
		return action.getPlayerName().equals(game.getActivePlayer().getName());
	}

}
