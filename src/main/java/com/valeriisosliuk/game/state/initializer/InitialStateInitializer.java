package com.valeriisosliuk.game.state.initializer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.PlayerHolder;
import com.valeriisosliuk.game.state.actionhandler.GameStartActionHandler;

@Component
public class InitialStateInitializer extends AbstractStateInitializer {
	
	private static final Logger log = Logger.getLogger(InitialStateInitializer.class);
	
	@Autowired
	GameStartActionHandler startActionHandler;
	/*
	@Override
	public void applyAction(Game game, Action action) {
		if (validate(game, action)) {
			State state = startActionHandler.handleAction(game, action);
			game.setState(state);
		}
		
	}
	*/
	protected boolean validatePlayer(Game game, Action action) {
		if  (game.getPlayers().size() < PlayerHolder.MAX_PLAYERS) {
			return true;
		} else {
			log.error("Cannot join table. Max player count is reached");
			return false;
		}
	}

	
	@Override
	public void initializeState(Game game) {
		log.info("Game init");
	}


}
