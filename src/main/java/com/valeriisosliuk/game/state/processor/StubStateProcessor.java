package com.valeriisosliuk.game.state.processor;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;

@Component
public class StubStateProcessor implements StateProcessor{

	@Override
	public void initializeState(Game game) {
		throw new UnsupportedOperationException("Calling initializeState on stub StateProcessor");
	}

	@Override
	public void applyAction(Game game, Action action) {
		throw new UnsupportedOperationException("Calling applyAction on stub StateProcessor");
		
	}

}
