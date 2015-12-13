package com.valeriisosliuk.game.state.actionhandler;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;

@Component
public class StubActionHandler implements ActionHandler {

	@Override
	public State handleAction(Game game, Action action) {
		throw new UnsupportedOperationException("Calling handleAction on stub ActionHandler");
	}

}
