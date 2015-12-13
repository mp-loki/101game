package com.valeriisosliuk.game.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.actionhandler.ActionHandler;
import com.valeriisosliuk.model.ActionType;

@Component
public class ActionService {

	private static final Logger log = Logger.getLogger(ActionService.class);

	@Resource(name = "actionHandlers")
	private Map<ActionType, ActionHandler> actionHandlers;

	public void handleAction(Game game, Action action) {
		if (validateAction(game, action)) {
			ActionHandler actionHandler = actionHandlers.get(action.getType());
			if (actionHandler != null) {
				game.setState(actionHandler.handleAction(game, action));
			} else {
				log.warn("No action handler found for ActionType: " + action.getType());
			}
		} else {
			log.warn("Unable to process action - it's not " + action.getPlayerName() + "'s turn");
		}
	}

	private boolean validateAction(Game game, Action action) {
		return action.getPlayerName().equals(game.getActivePlayer().getName());
	}
}
