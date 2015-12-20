package com.valeriisosliuk.game.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.ActionType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class GameServiceIntegrationTest {
	
	@Resource
	private GameService gameService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private ActionService actionService;
	
	@Test
	public void testSinglePlayerJoined() {
		userService.addUser("Kyle");
		Game game = gameService.getGame("Kyle");
		Action action = new Action(ActionType.START, "Kyle");
		actionService.handleAction(game, action);
		assertEquals(State.INITIAL, game.getState());

		gameService.dismissGame(game);
		userService.removeUser("Kyle");
	}
	
	@Test
	public void testTwoPlayersJoined() {
		userService.addUser("Kyle");
		userService.addUser("Stan");
		
		Game kyleGame = gameService.getGame("Kyle");
		Action kyleAction = new Action(ActionType.START, "Kyle");
		actionService.handleAction(kyleGame, kyleAction);
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game stanGame = gameService.getGame("Stan");
		Action stanAction = new Action(ActionType.START, "Stan");
		actionService.handleAction(stanGame, stanAction);
		
		assertTrue(stanGame.equals(kyleGame));
		assertEquals(State.TURN_START, kyleGame.getState());
		
		gameService.dismissGame(kyleGame);
		userService.removeUser("Kyle");
		userService.removeUser("Stan");
	}
	
	@Test
	public void testThreePlayersJoinedTwoStarted() {
		userService.addUser("Kyle");
		userService.addUser("Stan");
		userService.addUser("Cartman");
		
		Game kyleGame = gameService.getGame("Kyle");
		Action kyleAction = new Action(ActionType.START, "Kyle");
		actionService.handleAction(kyleGame, kyleAction);
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game stanGame = gameService.getGame("Stan");
		Action stanAction = new Action(ActionType.START, "Stan");
		actionService.handleAction(stanGame, stanAction);
		
		assertTrue(stanGame.equals(kyleGame));
		assertEquals(State.INITIAL, kyleGame.getState());

		gameService.dismissGame(kyleGame);
		userService.removeUser("Kyle");
		userService.removeUser("Stan");
		userService.removeUser("Cartman");
	}
	
	@Test
	public void testThreePlayersJoinedThreeStarted() {
		assertTrue(CollectionUtils.isEmpty(userService.getLoggedInUsers()));
		userService.addUser("Kyle");
		userService.addUser("Stan");
		userService.addUser("Cartman");
		
		Game kyleGame = gameService.getGame("Kyle");
		Action kyleAction = new Action(ActionType.START, "Kyle");
		actionService.handleAction(kyleGame, kyleAction);
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game stanGame = gameService.getGame("Stan");
		Action stanAction = new Action(ActionType.START, "Stan");
		actionService.handleAction(stanGame, stanAction);
		
		assertTrue(stanGame.equals(kyleGame));
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game cartmanGame = gameService.getGame("Cartman");
		Action cartmanAction = new Action(ActionType.START, "Cartman");
		actionService.handleAction(cartmanGame, cartmanAction);
		
		assertTrue(stanGame.equals(cartmanGame));
		assertEquals(State.TURN_START, kyleGame.getState());
		
		gameService.dismissGame(kyleGame);
		userService.removeUser("Kyle");
		userService.removeUser("Stan");
		userService.removeUser("Cartman");
	}
	
	@Test
	public void testFourPlayersJoinedThreeStarted() {
		userService.addUser("Kyle");
		userService.addUser("Stan");
		userService.addUser("Cartman");
		userService.addUser("Wendy");
		
		Game kyleGame = gameService.getGame("Kyle");
		Action kyleAction = new Action(ActionType.START, "Kyle");
		actionService.handleAction(kyleGame, kyleAction);
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game stanGame = gameService.getGame("Stan");
		Action stanAction = new Action(ActionType.START, "Stan");
		actionService.handleAction(stanGame, stanAction);
		
		assertTrue(stanGame.equals(kyleGame));
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game cartmanGame = gameService.getGame("Cartman");
		Action cartmanAction = new Action(ActionType.START, "Cartman");
		actionService.handleAction(cartmanGame, cartmanAction);
		
		assertTrue(stanGame.equals(cartmanGame));
		assertEquals(State.INITIAL, kyleGame.getState());
		
		gameService.dismissGame(kyleGame);
		userService.removeUser("Kyle");
		userService.removeUser("Stan");
		userService.removeUser("Cartman");
		userService.removeUser("Wendy");
	}
	@Test
	public void testFourPlayersJoinedFourStarted() {
		userService.addUser("Kyle");
		userService.addUser("Stan");
		userService.addUser("Cartman");
		userService.addUser("Wendy");
		
		Game kyleGame = gameService.getGame("Kyle");
		Action kyleAction = new Action(ActionType.START, "Kyle");
		actionService.handleAction(kyleGame, kyleAction);
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game stanGame = gameService.getGame("Stan");
		Action stanAction = new Action(ActionType.START, "Stan");
		actionService.handleAction(stanGame, stanAction);
		
		assertTrue(stanGame.equals(kyleGame));
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game cartmanGame = gameService.getGame("Cartman");
		Action cartmanAction = new Action(ActionType.START, "Cartman");
		actionService.handleAction(cartmanGame, cartmanAction);
		
		assertTrue(cartmanGame.equals(kyleGame));
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game wendyGame = gameService.getGame("Wendy");
		Action wendyAction = new Action(ActionType.START, "Wendy");
		actionService.handleAction(wendyGame, wendyAction);
		
		assertTrue(wendyGame.equals(kyleGame));
		assertEquals(State.TURN_START, kyleGame.getState());
		
		gameService.dismissGame(kyleGame);
		userService.removeUser("Kyle");
		userService.removeUser("Stan");
		userService.removeUser("Cartman");
		userService.removeUser("Wendy");
	}
	
	@Test
	public void testFivePlayersJoinedFiveStarted() {
		userService.addUser("Kyle");
		userService.addUser("Stan");
		userService.addUser("Cartman");
		userService.addUser("Kenny");
		userService.addUser("Wendy");
		
		Game kyleGame = gameService.getGame("Kyle");
		Action kyleAction = new Action(ActionType.START, "Kyle");
		actionService.handleAction(kyleGame, kyleAction);
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game stanGame = gameService.getGame("Stan");
		Action stanAction = new Action(ActionType.START, "Stan");
		actionService.handleAction(stanGame, stanAction);
		
		assertTrue(stanGame.equals(kyleGame));
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game cartmanGame = gameService.getGame("Cartman");
		Action cartmanAction = new Action(ActionType.START, "Cartman");
		actionService.handleAction(cartmanGame, cartmanAction);
		
		assertTrue(cartmanGame.equals(kyleGame));
		assertEquals(State.INITIAL, kyleGame.getState());
		Game kennyGame = gameService.getGame("Kenny");
		Action kennyAction = new Action(ActionType.START, "Kenny");
		actionService.handleAction(kennyGame, kennyAction);
		
		assertTrue(kennyGame.equals(kyleGame));
		assertEquals(State.TURN_START, kyleGame.getState());
		
		Game wendyGame = gameService.getGame("Wendy");
		Action wendyAction = new Action(ActionType.START, "Wendy");
		actionService.handleAction(wendyGame, wendyAction);
		
		assertFalse(wendyGame.equals(kyleGame));
		assertEquals(State.INITIAL, wendyGame.getState());
		
		gameService.dismissGame(kyleGame);
		gameService.dismissGame(wendyGame);
		userService.removeUser("Kyle");
		userService.removeUser("Stan");
		userService.removeUser("Cartman");
		userService.removeUser("Kenny");
		userService.removeUser("Wendy");
	}
	
	@Test
	public void testTwoPlayersJoinedAndThenOtherTwo() {
		userService.addUser("Kyle");
		userService.addUser("Stan");
		
		Game kyleGame = gameService.getGame("Kyle");
		Action kyleAction = new Action(ActionType.START, "Kyle");
		actionService.handleAction(kyleGame, kyleAction);
		assertEquals(State.INITIAL, kyleGame.getState());
		
		Game stanGame = gameService.getGame("Stan");
		Action stanAction = new Action(ActionType.START, "Stan");
		actionService.handleAction(stanGame, stanAction);
		
		assertTrue(stanGame.equals(kyleGame));
		assertEquals(State.TURN_START, kyleGame.getState());
		
		userService.addUser("Cartman");
		userService.addUser("Wendy");
		Game cartmanGame = gameService.getGame("Cartman");
		Action cartmanAction = new Action(ActionType.START, "Cartman");
		actionService.handleAction(cartmanGame, cartmanAction);
		
		assertFalse(cartmanGame.equals(kyleGame));
		assertEquals(State.INITIAL, cartmanGame.getState());
		
		Game wendyGame = gameService.getGame("Wendy");
		Action wendyAction = new Action(ActionType.START, "Wendy");
		actionService.handleAction(wendyGame, wendyAction);
		
		assertTrue(wendyGame.equals(cartmanGame));
		assertEquals(State.TURN_START, cartmanGame.getState());
		
		gameService.dismissGame(kyleGame);
		gameService.dismissGame(cartmanGame);
		userService.removeUser("Kyle");
		userService.removeUser("Stan");
		userService.removeUser("Cartman");
		userService.removeUser("Wendy");
	}
}
