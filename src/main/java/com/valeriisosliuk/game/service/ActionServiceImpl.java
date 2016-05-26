package com.valeriisosliuk.game.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.ActionType;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.actionhandler.ActionHandler;

@Component
public class ActionServiceImpl implements ActionService {

	private static final Logger log = Logger.getLogger(ActionServiceImpl.class);
	
	@Autowired
	private GameService gameService;
	
	@Resource(name = "actionHandlers")
	private Map<ActionType, ActionHandler> actionHandlers;

	@Override
	public void handleAction(Game game, Action action) {
		if (validateAction(game, action)) {
			ActionHandler actionHandler = actionHandlers.get(action.getType());
			if (actionHandler != null) {
				State nextState = actionHandler.handleAction(game, action);
				if (nextState == State.GAME_OVER) {
					gameService.dismissGame(game);
				}
				game.setState(nextState);
			} else {
				log.warn("No action handler found for ActionType: " + action.getType());
			}
		} else {
			log.warn("Unable to process action - it's not " + action.getPlayerName() + "'s turn");
		}
	}

	private boolean validateAction(Game game, Action action) {
		if (action.getType() == ActionType.START || action.getType() == ActionType.QUIT) {
			return true;
		}
		boolean valid = action.getPlayerName().equals(game.getActivePlayer().getName());
		if (valid && action.getType() == ActionType.ACTION && game.getState() == State.RESPOND_SUIT) {
		    action.setType(ActionType.RESPOND);
		    
		}
		return valid;
	}
}
