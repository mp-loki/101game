package com.valeriisosliuk.game.state.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.state.State;

@Component
public class StateProcessors {
	
	@Autowired
	private InitialStateProcessor initialStateProcessor;
	@Autowired
	private DealStartProcessor dealStartProcessor;
	@Autowired
	private TurnStartProcessor turnStartProcessor;
	@Autowired 
	private StubStateProcessor stubStateProcessor;
	
	public StateProcessor getStateProcessor(State state) {
		switch(state) {
			case INITIAL    : return initialStateProcessor;
			case DEAL_START : return dealStartProcessor;
			case TURN_START : return turnStartProcessor;
			default         : return stubStateProcessor;
		}
	}
}
