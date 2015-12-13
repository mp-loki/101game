package com.valeriisosliuk.game.state.actionhandler;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.ActionType;

public class GameStartActionHandlerTest {
	
	private ActionHandler actionHandler;
	
	@Before
	public void setUp() {
		actionHandler = new GameStartActionHandler();
	}
	
	@Test
	public void testGameStart() {
		Action firstAction = new Action(ActionType.START, "Kyle");
		Action secondAction = new Action(ActionType.START, "Stan");
		Action thirdAction = new Action(ActionType.START, "Cartman");
		Action fourthAction = new Action(ActionType.START, "Kenny");
		Game game = new Game();
		assertEquals(State.INITIAL, actionHandler.handleAction(game, firstAction));
		assertEquals(State.INITIAL, actionHandler.handleAction(game, secondAction));
		assertEquals(State.INITIAL, actionHandler.handleAction(game, thirdAction));
		assertEquals(State.DEAL_START, actionHandler.handleAction(game, fourthAction));
	}

}
