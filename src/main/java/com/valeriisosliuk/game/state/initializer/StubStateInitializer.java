package com.valeriisosliuk.game.state.initializer;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;

@Component
public class StubStateInitializer implements StateInitinalizer{

	@Override
	public void initializeState(Game game) {
		throw new UnsupportedOperationException("Calling initializeState on stub StateProcessor");
	}
}
