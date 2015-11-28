package com.valeriisosliuk.game.state.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.PlayerHolder;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.actionhandler.StartActionHandler;
import com.valeriisosliuk.model.ActionType;

@Component
public class InitialStateProcessor extends AbstractStateProcessor {
	
	private static final Logger log = Logger.getLogger(InitialStateProcessor.class);
	
	@Autowired
	StartActionHandler startActionHandler;
	
	@Override
	public void applyAction(Game game, Action action) {
		if (validate(game, action)) {
			State state = startActionHandler.handleAction(game, action);
			game.setState(state);
		}
		
	}
	protected boolean validatePlayer(Game game, Action action) {
		if  (game.getPlayers().size() < PlayerHolder.MAX_PLAYERS) {
			return true;
		} else {
			log.error("Cannot join table. Max player count is reached");
			return false;
		}
	}

	@Override
	protected boolean validateAction(Action action) {
		if  (action.getType() == ActionType.START) {
			return true;
		} else {
			log.error("Action " + action.getType() + "is not applicable for InitialStateProcessor");
			return false;
		}
	}
	
	@Override
	public void initializeState(Game game) {
		log.info("Game init");
	}


}
