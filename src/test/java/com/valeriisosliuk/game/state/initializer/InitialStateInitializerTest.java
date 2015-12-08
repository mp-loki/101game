package com.valeriisosliuk.game.state.initializer;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.initializer.InitialStateInitializer;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;
import com.valeriisosliuk.model.ActionType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class InitialStateInitializerTest {
	
	
	@Autowired
	private InitialStateInitializer stateProcessor;
	
	@Ignore
	@Test
	public void testSwitchingFromInitialToStartState() {
		Game game  = new Game();
		assertEquals(game.getState(), State.INITIAL);
		Action action1 = getAction("Kyle");
		Action action2 = getAction("Stan");
		Action action3 = getAction("Cartman");
		//stateProcessor.applyAction(game, action1);
		assertEquals(State.INITIAL, game.getState());
		//stateProcessor.applyAction(game, action2);
		assertEquals(State.INITIAL, game.getState());
		//stateProcessor.applyAction(game, action3);
		assertEquals(State.DEAL_START, game.getState());
	}

	private Action getAction(String name) {
		Action action = new Action();
		action.setCurrentPlayer(name);
		action.setType(ActionType.START);
		return action;
	}

}
