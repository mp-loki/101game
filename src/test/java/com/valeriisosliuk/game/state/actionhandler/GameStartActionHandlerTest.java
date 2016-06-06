package com.valeriisosliuk.game.state.actionhandler;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.ActionType;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.service.UserHolder;
import com.valeriisosliuk.game.state.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.valeriisosliuk.game.Application.class, loader = AnnotationConfigContextLoader.class)
public class GameStartActionHandlerTest {

	@Resource
	private UserHolder userHolder;

	@Resource
	private ActionHandler gameStartActionHandler;
	
	@Autowired
	private Game game;

	@Before
	public void setUp() {
		userHolder.addUser("Kyle");
		userHolder.addUser("Stan");
		userHolder.addUser("Cartman");
		userHolder.addUser("Kenny");
	}
	
	@After
	public void tearDown() {
		userHolder.removeUser("Kyle");
		userHolder.removeUser("Stan");
		userHolder.removeUser("Cartman");
		userHolder.removeUser("Kenny");
	}

	@Test
	public void testGameStart() {
		Action firstAction = new Action(ActionType.START, "Kyle");
		Action secondAction = new Action(ActionType.START, "Stan");
		Action thirdAction = new Action(ActionType.START, "Cartman");
		Action fourthAction = new Action(ActionType.START, "Kenny");
		game.joinGame("Kyle");
		assertEquals(State.INITIAL, gameStartActionHandler.handleAction(game, firstAction));
		game.joinGame("Stan");
		assertEquals(State.INITIAL, gameStartActionHandler.handleAction(game, secondAction));
		game.joinGame("Cartman");
		assertEquals(State.INITIAL, gameStartActionHandler.handleAction(game, thirdAction));
		game.joinGame("Kenny");
		assertEquals(State.DEAL_START, gameStartActionHandler.handleAction(game, fourthAction));
	}

}
