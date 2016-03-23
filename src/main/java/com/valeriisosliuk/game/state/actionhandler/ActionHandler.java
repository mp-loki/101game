package com.valeriisosliuk.game.state.actionhandler;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.state.State;

public interface ActionHandler {
	
	State handleAction(Game game, Action action);
	
}
