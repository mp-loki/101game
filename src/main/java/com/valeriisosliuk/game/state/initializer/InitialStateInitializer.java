package com.valeriisosliuk.game.state.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.PendingStartDto;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.service.MessageService;
import com.valeriisosliuk.game.service.UserService;

@Component
@Qualifier("initialStateInitializer")
public class InitialStateInitializer implements StateInitinalizer {
    
    @Autowired
    private MessageService messageservice;
    
    @Autowired
    private UserService userService;
    
	
	@Override
	public void initializeState(Game game) {
	    PendingStartDto dto = new PendingStartDto(userService.getPendingPlayers(game));
	    messageservice.sendToAll(dto);
	}

}
