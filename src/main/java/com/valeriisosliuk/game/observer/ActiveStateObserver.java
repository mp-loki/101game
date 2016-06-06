package com.valeriisosliuk.game.observer;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.valeriisosliuk.game.dto.ActiveStateDto;
import com.valeriisosliuk.game.service.MessageService;
import com.valeriisosliuk.game.state.ActiveState;

public class ActiveStateObserver implements Observer {

	private static final Logger log = Logger.getLogger(ActiveStateObserver.class);
	
	@Autowired
	private MessageService messageService;
	
	public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
	public void update(Observable observable, Object arg) {
	    log.info("Updated object: " + observable + "with argument: " + arg);
	    ActiveState activeState = (ActiveState) observable;
	    ActiveStateDto dto = new ActiveStateDto(activeState.isPickAllowed(), activeState.isPassAllowed(), activeState.getTurnOptions());
	    messageService.send(activeState.getName(), dto);
	}

}
