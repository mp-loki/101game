package com.valeriisosliuk.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.model.ActionType;

@Component
public class ActionHandlerSupplier {
	@Autowired
	@Qualifier("passHandler")
	private ActionHandler passHandler;
	
	@Autowired 
	@Qualifier("pickHandler")
	private ActionHandler pickHandler;
	
	@Autowired
	@Qualifier("startHandler")
	private ActionHandler startHandler;
	
	@Autowired
	@Qualifier("cardMoveHandlerOld")
	private ActionHandler cardMoveHandler;
	
	
	public ActionHandler getActionHandler(ActionType actionType) {
		ActionHandler handler = null;
		switch (actionType) {
		case PASS:  handler = passHandler;
					break;
		case PICK:  handler = pickHandler;
					break;
		case START: handler = startHandler;
					break;
		case ACTION: handler = cardMoveHandler;
					break;
		}
		return handler;
	}
}
