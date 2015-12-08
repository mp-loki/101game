package com.valeriisosliuk.game.state.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.state.State;

@Component
public class StateProcessors {
	
	@Autowired
	private InitialStateInitializer initialStateProcessor;
	@Autowired
	private DealStartInitializer dealStartProcessor;
	@Autowired
	private TurnStartInitializer turnStartProcessor;
	@Autowired 
	private StubStateInitializer stubStateProcessor;
	
	public StateInitinalizer getStateProcessor(State state) {
		switch(state) {
			case INITIAL    : return initialStateProcessor;
			case DEAL_START : return dealStartProcessor;
			case TURN_START : return turnStartProcessor;
			default         : return stubStateProcessor;
		}
	}
}
