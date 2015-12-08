package com.valeriisosliuk.game.state.initializer;

import org.apache.log4j.Logger;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;

public abstract class AbstractStateInitializer implements StateInitinalizer {
	
	private static final Logger log = Logger.getLogger(AbstractStateInitializer.class);
	
		
	protected boolean validatePlayer(Game game, Action action) {
		return action.getPlayer().equals(game.getActivePlayer().getName());
	}
	
	//protected abstract boolean validateAction(Action action);
	/*
	protected boolean validate(Game game, Action action) {
		return validateAction(action) && validatePlayer(game, action);
	}
	*/

}
