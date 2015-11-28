package com.valeriisosliuk.game.state.actionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;

import static com.valeriisosliuk.model.Card.*;
import static org.junit.Assert.*;

import com.valeriisosliuk.model.CardDeck;

public class CardMoveHandlerTest {
	
	private Game game;
	private CardMoveHandler cardMoveHandler;
	
	@Before
	public void setUp() {
		cardMoveHandler = new CardMoveHandler();
		game = new Game();
        game.joinGame("Kyle");
        List<Card> cards = new LinkedList<Card>();
        cards.add(ACE_OF_HEARTS);
        cards.add(KING_OF_CLUBS);
        
        CardDeck cardDeck = new CardDeck(cards);
        CardHolder holder = new CardHolder();
        game.setCardHolder(holder);
        holder.setCardDeck(cardDeck);
	}
	
	@Test
	public void testCardMove() {
		Set<Card> hand = new HashSet<Card>(Arrays.asList(_9_OF_HEARTS, _9_OF_SPADES, _7_OF_CLUBS, KING_OF_SPADES));
		game.getActivePlayer().setHand(hand);
		Action firstAction = new Action(ActionType.MOVE, _9_OF_HEARTS, "Kyle");
		assertEquals(State.TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(new HashSet<>(Arrays.asList(_9_OF_SPADES, _7_OF_CLUBS, KING_OF_SPADES)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<>(Arrays.asList(_9_OF_HEARTS)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(Arrays.asList(_9_OF_SPADES)), game.getActivePlayer().getActiveState().getTurnOptions());
		assertFalse(game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue(game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action secondAction = new Action(ActionType.MOVE, _9_OF_SPADES, "Kyle");
		assertEquals(State.TURN_END, cardMoveHandler.handleAction(game, secondAction));
		assertEquals(new HashSet<>(Arrays.asList(_9_OF_HEARTS, _9_OF_SPADES)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertTrue(CollectionUtils.isEmpty(game.getActivePlayer().getActiveState().getTurnOptions()));
	}
	
	@Test
	public void testJackCardMove() {
		Set<Card> hand = new HashSet<Card>(Arrays.asList(JACK_OF_CLUBS, JACK_OF_SPADES, _7_OF_CLUBS, KING_OF_SPADES));
		game.getActivePlayer().setHand(hand);
		Action firstAction = new Action(ActionType.MOVE, JACK_OF_CLUBS, "Kyle");
		assertEquals(State.TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(new HashSet<>(Arrays.asList(JACK_OF_SPADES, _7_OF_CLUBS, KING_OF_SPADES)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<>(Arrays.asList(JACK_OF_CLUBS)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(Arrays.asList(JACK_OF_SPADES)), game.getActivePlayer().getActiveState().getTurnOptions());
		assertFalse(game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue(game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action secondAction = new Action(ActionType.END, null, "Kyle");
		assertEquals(State.TURN_END, cardMoveHandler.handleAction(game, secondAction));
	}
	@Test
	public void testJackNotAvailableAfterFirstMove() {
		Set<Card> hand = new HashSet<Card>(Arrays.asList(JACK_OF_CLUBS, JACK_OF_SPADES, _7_OF_HEARTS, _7_OF_DIAMONDS));
		game.getActivePlayer().setHand(hand);
		Action firstAction = new Action(ActionType.MOVE, _7_OF_HEARTS, "Kyle");
		assertEquals(State.TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(new HashSet<>(Arrays.asList(_7_OF_DIAMONDS, JACK_OF_CLUBS, JACK_OF_SPADES)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<>(Arrays.asList(_7_OF_HEARTS)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(Arrays.asList(_7_OF_DIAMONDS)), game.getActivePlayer().getActiveState().getTurnOptions());
		assertFalse(game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue(game.getActivePlayer().getActiveState().isPassAllowed());
	}
	
	@Test
	public void testSixCardMove() {
		Set<Card> hand = new HashSet<Card>(Arrays.asList(_6_OF_HEARTS, _6_OF_SPADES, _10_OF_HEARTS, _7_OF_DIAMONDS));
		game.getActivePlayer().setHand(hand);
		Action firstAction = new Action(ActionType.MOVE, _6_OF_HEARTS, "Kyle");
		assertEquals(State.TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_SPADES, _10_OF_HEARTS, _7_OF_DIAMONDS)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_HEARTS)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_SPADES)), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue(game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse(game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action secondAction = new Action(ActionType.MOVE, _6_OF_SPADES, "Kyle");
		assertEquals(State.TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, secondAction));
		assertEquals(new HashSet<>(Arrays.asList(_10_OF_HEARTS, _7_OF_DIAMONDS)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_HEARTS, _6_OF_SPADES)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue(game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse(game.getActivePlayer().getActiveState().isPassAllowed());
		
		game.getActivePlayer().pickCard(JACK_OF_CLUBS);
		Action thirdAction = new Action(ActionType.MOVE, JACK_OF_CLUBS, "Kyle");
		
		assertEquals(State.TURN_END, cardMoveHandler.handleAction(game, thirdAction));
		assertEquals(new HashSet<>(Arrays.asList(_10_OF_HEARTS, _7_OF_DIAMONDS)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_HEARTS, _6_OF_SPADES, JACK_OF_CLUBS)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(), game.getActivePlayer().getActiveState().getTurnOptions());
		assertFalse(game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse(game.getActivePlayer().getActiveState().isPassAllowed());
	}
	
	@Test
	public void testSixCardMoveSeveralCardsToCover() {
		Set<Card> hand = new HashSet<Card>(Arrays.asList(_6_OF_HEARTS, _6_OF_SPADES, _9_OF_SPADES, _9_OF_DIAMONDS));
		game.getActivePlayer().setHand(hand);
		Action firstAction = new Action(ActionType.MOVE, _6_OF_HEARTS, "Kyle");
		assertEquals(State.TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_SPADES, _9_OF_SPADES, _9_OF_DIAMONDS)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_HEARTS)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_SPADES)), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue(game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse(game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action secondAction = new Action(ActionType.MOVE, _6_OF_SPADES, "Kyle");
		assertEquals(State.TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, secondAction));
		assertEquals(new HashSet<>(Arrays.asList(_9_OF_SPADES, _9_OF_DIAMONDS)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_HEARTS, _6_OF_SPADES)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(Arrays.asList(_9_OF_SPADES)), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue(game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse(game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action thirdAction = new Action(ActionType.MOVE, _9_OF_SPADES, "Kyle");
		assertEquals(State.TURN_END, cardMoveHandler.handleAction(game, thirdAction));
		assertEquals(new HashSet<>(Arrays.asList(_9_OF_DIAMONDS)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_HEARTS, _6_OF_SPADES, _9_OF_SPADES)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(), game.getActivePlayer().getActiveState().getTurnOptions());
		assertFalse(game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue(game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action fourthAction = new Action(ActionType.MOVE, _9_OF_DIAMONDS, "Kyle");
		assertEquals(State.TURN_END, cardMoveHandler.handleAction(game, fourthAction));
		assertEquals(new HashSet<>(), game.getActivePlayer().getHand());
		assertEquals(new HashSet<>(Arrays.asList(_6_OF_HEARTS, _6_OF_SPADES, _9_OF_SPADES, _9_OF_DIAMONDS)), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(), game.getActivePlayer().getActiveState().getTurnOptions());
		assertFalse(game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue(game.getActivePlayer().getActiveState().isPassAllowed());
		
		
	}

}
