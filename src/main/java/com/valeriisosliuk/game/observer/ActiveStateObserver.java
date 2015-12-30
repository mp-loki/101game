package com.valeriisosliuk.game.observer;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import com.valeriisosliuk.dto.ActiveStateDto;
import com.valeriisosliuk.game.service.MessageServiceLocator;
import com.valeriisosliuk.game.state.ActiveState;

public class ActiveStateObserver implements Observer {

	private static final Logger log = Logger.getLogger(ActiveStateObserver.class);
	
	@Override
	public void update(Observable observable, Object arg) {
	    log.info("Updated object: " + observable + "with argument: " + arg);
	    ActiveState activeState = (ActiveState) observable;
	    ActiveStateDto dto = new ActiveStateDto(activeState.isPickAllowed(), activeState.isPassAllowed(), activeState.getTurnOptions());
	    MessageServiceLocator.getMessageService().send(activeState.getName(), dto);
	}

}
