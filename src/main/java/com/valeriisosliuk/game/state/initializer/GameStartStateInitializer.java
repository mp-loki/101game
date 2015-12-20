package com.valeriisosliuk.game.state.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.service.MessageService;

@Component("gameStartStateInitializer")
public class GameStartStateInitializer implements StateInitinalizer {

	@Autowired
	private MessageService messageService;
	
	@Override
	public void initializeState(Game game) {
		
	}

}
