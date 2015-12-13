package com.valeriisosliuk.game.state.initializer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;

@Component("initialStateInitializer")
public class InitialStateInitializer implements StateInitinalizer {
	
	private static final Logger log = Logger.getLogger(InitialStateInitializer.class);
	
	@Override
	public void initializeState(Game game) {
		log.info("Game init");
	}

}
