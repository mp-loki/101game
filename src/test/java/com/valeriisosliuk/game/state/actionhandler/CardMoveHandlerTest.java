package com.valeriisosliuk.game.state.actionhandler;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.CollectionUtils;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.CardHolder;

import static com.valeriisosliuk.game.state.State.*;

import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;

import static com.valeriisosliuk.model.Card.*;
import static org.junit.Assert.*;

import com.valeriisosliuk.model.CardDeck;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class CardMoveHandlerTest {
	
	private Game game;
	
	@Autowired
	private CardMoveActionHandler cardMoveHandler;
	
	@Before
	public void setUp() {
		game = new Game();
        game.joinGame("Kyle");
        game.setState(TURN_START);
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
		Action firstAction = new Action(ActionType.ACTION, _9_OF_HEARTS, "Kyle");
		assertEquals(TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(EnumSet.of(_9_OF_SPADES, _7_OF_CLUBS, KING_OF_SPADES), game.getActivePlayer().getHand());
		assertEquals(EnumSet.of(_9_OF_HEARTS), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(EnumSet.of(_9_OF_SPADES), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue("Pass is not allowed when it should be", game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action secondAction = new Action(ActionType.ACTION, _9_OF_SPADES, "Kyle");
		assertEquals(TURN_END, cardMoveHandler.handleAction(game, secondAction));
		assertEquals(EnumSet.of(_9_OF_HEARTS, _9_OF_SPADES), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertTrue("Turn options should be empty", CollectionUtils.isEmpty(game.getActivePlayer().getActiveState().getTurnOptions()));
	}
	
	@Test
	public void testJackCardMove() {
		Set<Card> hand = new HashSet<Card>(Arrays.asList(JACK_OF_CLUBS, JACK_OF_SPADES, _7_OF_CLUBS, KING_OF_SPADES));
		game.getActivePlayer().setHand(hand);
		Action firstAction = new Action(ActionType.ACTION, JACK_OF_CLUBS, "Kyle");
		assertEquals(TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(EnumSet.of(JACK_OF_SPADES, _7_OF_CLUBS, KING_OF_SPADES), game.getActivePlayer().getHand());
		assertEquals(EnumSet.of(JACK_OF_CLUBS), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(EnumSet.of(JACK_OF_SPADES), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue("Pass is not allowed when it should be", game.getActivePlayer().getActiveState().isPassAllowed());
	}
	
	@Test
	public void testJackNotAvailableAfterFirstMove() {
		Set<Card> hand = new HashSet<Card>(Arrays.asList(JACK_OF_CLUBS, JACK_OF_SPADES, _7_OF_HEARTS, _7_OF_DIAMONDS));
		game.getActivePlayer().setHand(hand);
		Action firstAction = new Action(ActionType.ACTION, _7_OF_HEARTS, "Kyle");
		assertEquals(TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(EnumSet.of(_7_OF_DIAMONDS, JACK_OF_CLUBS, JACK_OF_SPADES), game.getActivePlayer().getHand());
		assertEquals(EnumSet.of(_7_OF_HEARTS), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(EnumSet.of(_7_OF_DIAMONDS), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue("Pass is not allowed when it should be", game.getActivePlayer().getActiveState().isPassAllowed());
	}
	
	@Test
	public void testSixCardMove() {
		Set<Card> hand = new HashSet<Card>(Arrays.asList(_6_OF_HEARTS, _6_OF_SPADES, _10_OF_HEARTS, _7_OF_DIAMONDS, JACK_OF_CLUBS));
		game.getActivePlayer().setHand(hand);
		Action firstAction = new Action(ActionType.ACTION, _6_OF_HEARTS, "Kyle");
		assertEquals(TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(EnumSet.of(_6_OF_SPADES, _10_OF_HEARTS, _7_OF_DIAMONDS, JACK_OF_CLUBS), game.getActivePlayer().getHand());
		assertEquals(EnumSet.of(_6_OF_HEARTS), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(EnumSet.of(_6_OF_SPADES, _10_OF_HEARTS, JACK_OF_CLUBS), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse("Pass is allowed when it should not be", game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action secondAction = new Action(ActionType.ACTION, _6_OF_SPADES, "Kyle");
		assertEquals(TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, secondAction));
		assertEquals(EnumSet.of(_10_OF_HEARTS, _7_OF_DIAMONDS, JACK_OF_CLUBS), game.getActivePlayer().getHand());
		assertEquals(EnumSet.of(_6_OF_HEARTS, _6_OF_SPADES), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(EnumSet.of(JACK_OF_CLUBS), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse("Pass is allowed when it should not be", game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action thirdAction = new Action(ActionType.ACTION, JACK_OF_CLUBS, "Kyle");
		
		assertEquals(TURN_END, cardMoveHandler.handleAction(game, thirdAction));
		assertEquals(EnumSet.of(_10_OF_HEARTS, _7_OF_DIAMONDS), game.getActivePlayer().getHand());
		assertEquals(EnumSet.of(_6_OF_HEARTS, _6_OF_SPADES, JACK_OF_CLUBS), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is allowed when it should not be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse("Pass is allowed when it should not be", game.getActivePlayer().getActiveState().isPassAllowed());
	}
	
	@Test
	public void testSixCardMoveSeveralCardsToCover() {
		Set<Card> hand = new HashSet<Card>(Arrays.asList(_6_OF_HEARTS, _6_OF_SPADES, _9_OF_SPADES, _9_OF_DIAMONDS));
		game.getActivePlayer().setHand(hand);
		Action firstAction = new Action(ActionType.ACTION, _6_OF_HEARTS, "Kyle");
		assertEquals(TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(EnumSet.of(_6_OF_SPADES, _9_OF_SPADES, _9_OF_DIAMONDS), game.getActivePlayer().getHand());
		assertEquals(EnumSet.of(_6_OF_HEARTS), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(EnumSet.of(_6_OF_SPADES), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse("Pass is allowed when it should not be", game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action secondAction = new Action(ActionType.ACTION, _6_OF_SPADES, "Kyle");
		assertEquals(TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, secondAction));
		assertEquals(EnumSet.of(_9_OF_SPADES, _9_OF_DIAMONDS), game.getActivePlayer().getHand());
		assertEquals(EnumSet.of(_6_OF_HEARTS, _6_OF_SPADES), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(EnumSet.of(_9_OF_SPADES), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse("Pass is allowed when it should not be", game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action thirdAction = new Action(ActionType.ACTION, _9_OF_SPADES, "Kyle");
		assertEquals(TURN_IN_PROGRESS, cardMoveHandler.handleAction(game, thirdAction));
		assertEquals(EnumSet.of(_9_OF_DIAMONDS), game.getActivePlayer().getHand());
		assertEquals(EnumSet.of(_6_OF_HEARTS, _6_OF_SPADES, _9_OF_SPADES), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(EnumSet.of(_9_OF_DIAMONDS), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue("Pass is allowed when it should not be", game.getActivePlayer().getActiveState().isPassAllowed());
		
		Action fourthAction = new Action(ActionType.ACTION, _9_OF_DIAMONDS, "Kyle");
		assertEquals(TURN_END, cardMoveHandler.handleAction(game, fourthAction));
		assertEquals(new HashSet<>(), game.getActivePlayer().getHand());
		assertEquals(EnumSet.of(_6_OF_HEARTS, _6_OF_SPADES, _9_OF_SPADES, _9_OF_DIAMONDS), game.getActivePlayer().getActiveState().getCurrentTurnCards());
		assertEquals(new HashSet<>(), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue("Pass is allowed when it should not be", game.getActivePlayer().getActiveState().isPassAllowed());
	}
	
	@Test
	public void testNoValidTurnOptions() {
		Set<Card> hand = new HashSet<Card>(Arrays.asList(_6_OF_SPADES, _9_OF_SPADES, _9_OF_DIAMONDS));
		game.getActivePlayer().setHand(hand);
		Action firstAction = new Action(ActionType.ACTION, _6_OF_SPADES, "Kyle");
		assertEquals(TURN_START, cardMoveHandler.handleAction(game, firstAction));
		assertEquals(hand, game.getActivePlayer().getHand());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertFalse("Pass is allowed when it should not be", game.getActivePlayer().getActiveState().isPassAllowed());
	}

}
