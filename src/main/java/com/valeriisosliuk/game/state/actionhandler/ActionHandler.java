package com.valeriisosliuk.game.state.actionhandler;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;

public interface ActionHandler {
	
	State handleAction(Game game, Action action);
	
	//State handleAction(PlayerHolder playerHolder, CardDeck cardDeck)

}
