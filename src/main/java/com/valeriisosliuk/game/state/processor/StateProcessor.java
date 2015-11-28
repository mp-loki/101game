package com.valeriisosliuk.game.state.processor;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;

public interface StateProcessor {
	
	void initializeState(Game game);
	void applyAction(Game game, Action action);
}
