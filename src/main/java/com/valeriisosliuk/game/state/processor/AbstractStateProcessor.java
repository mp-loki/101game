package com.valeriisosliuk.game.state.processor;

import org.apache.log4j.Logger;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;

public abstract class AbstractStateProcessor implements StateProcessor {
	
	private static final Logger log = Logger.getLogger(AbstractStateProcessor.class);
	
		
	protected boolean validatePlayer(Game game, Action action) {
		return action.getPlayer().equals(game.getActivePlayer().getName());
	}
	
	@Override
	public abstract void applyAction(Game game, Action action);
	
	protected abstract boolean validateAction(Action action);
	
	protected boolean validate(Game game, Action action) {
		return validateAction(action) && validatePlayer(game, action);
	}

}
