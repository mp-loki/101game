package com.valeriisosliuk.game.state.initializer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.Dto;
import com.valeriisosliuk.game.dto.DtoType;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.service.MessageService;

@Component("demandSuitStateInitializer")
public class DemandSuitStateInitializer implements StateInitinalizer {
	
	@Autowired
	private MessageService messageService;

    private static final Logger log = Logger.getLogger(DemandSuitStateInitializer.class);
    @Override
    public void initializeState(Game game) {
    	String playerName = game.getActivePlayer().getName();
        log.info("Sending suit demand message to " + game.getActivePlayer().getName());
        messageService.send(playerName, new Dto(DtoType.DEMAND_SUIT));
    }
}
