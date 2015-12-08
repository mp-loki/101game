package com.valeriisosliuk.game.state.initializer;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;

public interface StateInitinalizer {
	
	void initializeState(Game game);
	//void applyAction(Game game, Action action);
}
