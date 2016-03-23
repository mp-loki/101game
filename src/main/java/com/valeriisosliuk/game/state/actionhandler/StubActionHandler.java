package com.valeriisosliuk.game.state.actionhandler;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.state.State;

@Component("stubActionHandler")
public class StubActionHandler implements ActionHandler {

	@Override
	public State handleAction(Game game, Action action) {
		throw new UnsupportedOperationException("Calling handleAction on stub ActionHandler");
	}

}
