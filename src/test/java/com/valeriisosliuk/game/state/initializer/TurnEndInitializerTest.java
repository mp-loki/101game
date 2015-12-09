package com.valeriisosliuk.game.state.initializer;

import static com.valeriisosliuk.model.Card.KING_OF_CLUBS;
import static com.valeriisosliuk.model.Card._6_OF_CLUBS;
import static com.valeriisosliuk.model.Card._7_OF_DIAMONDS;
import static com.valeriisosliuk.model.Card.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class TurnEndInitializerTest {
	
	@Autowired
	private TurnEndInitializer turnEndInitializer;
	
	private Game game;
	@Before
	public void setUp() {
		game = new Game();
		game.joinGame("Kyle");
		game.joinGame("Stan");
		game.joinGame("Cartman");
		game.setCardHolder(getCustomCardHolder());
		initOneCardInHand(game);
		game.setState(State.TURN_END);
	}
	
	@Test
	public void testInitializeTurnEndState() {
		game.getActivePlayer().setHand(EnumSet.of(Card._9_OF_DIAMONDS));
		assertEquals("Kyle", game.getActivePlayer().getName());
		turnEndInitializer.initializeState(game);
		assertEquals(State.TURN_START, game.getState());
		assertEquals("Stan", game.getActivePlayer().getName());
	}
	
	@Test
	public void testEndTurnAce() {
		game.getActivePlayer().getActiveState().setCurrentTurnCards(EnumSet.of(Card.ACE_OF_CLUBS, Card.ACE_OF_HEARTS));
		assertEquals("Kyle", game.getActivePlayer().getName());
		turnEndInitializer.initializeState(game);
		assertEquals(State.TURN_START, game.getState());
		assertEquals("Cartman", game.getActivePlayer().getName());
	}
	@Test
	public void testEndTurnSingleEight() {
		game.getActivePlayer().getActiveState().setCurrentTurnCards(EnumSet.of(Card._8_OF_HEARTS));
		assertEquals("Kyle", game.getActivePlayer().getName());
		turnEndInitializer.initializeState(game);
		assertEquals(State.TURN_START, game.getState());
		Player skippedPlayer = game.getPlayers().get(1);
		assertEquals("Stan", skippedPlayer.getName());
		assertEquals(3, skippedPlayer.getHand().size());
		assertEquals(EnumSet.of(_9_OF_HEARTS, QUEEN_OF_DIAMONDS, QUEEN_OF_CLUBS), skippedPlayer.getHand());
		assertEquals("Cartman", game.getActivePlayer().getName());
	}
	
	@Test
	public void testEndTurnTwoEights() {
		game.getActivePlayer().getActiveState().setCurrentTurnCards(EnumSet.of(_8_OF_HEARTS, _8_OF_SPADES));
		assertEquals("Kyle", game.getActivePlayer().getName());
		turnEndInitializer.initializeState(game);
		assertEquals(State.TURN_START, game.getState());
		Player skippedPlayer = game.getPlayers().get(1);
		assertEquals("Stan", skippedPlayer.getName());
		assertEquals(5, skippedPlayer.getHand().size());
		assertEquals(EnumSet.of(_9_OF_HEARTS, QUEEN_OF_DIAMONDS, QUEEN_OF_CLUBS, KING_OF_CLUBS, JACK_OF_DIAMONDS), skippedPlayer.getHand());
		assertEquals("Cartman", game.getActivePlayer().getName());
	}
	
	@Test
	public void testEndTurnSingleSeven() {
		game.getActivePlayer().getActiveState().setCurrentTurnCards(EnumSet.of(_7_OF_HEARTS));
		assertEquals("Kyle", game.getActivePlayer().getName());
		turnEndInitializer.initializeState(game);
		assertEquals(State.TURN_START, game.getState());
		assertEquals("Stan", game.getActivePlayer().getName());
		assertEquals(2, game.getActivePlayer().getHand().size());
		assertEquals(EnumSet.of(_9_OF_HEARTS, QUEEN_OF_DIAMONDS), game.getActivePlayer().getHand());
	}
	
	@Test
	public void testEndTurnTwoSevens() {
		game.setCardHolder(getCustomCardHolder());
		game.getActivePlayer().getActiveState().setCurrentTurnCards(EnumSet.of(_7_OF_HEARTS, _7_OF_DIAMONDS));
		assertEquals("Kyle", game.getActivePlayer().getName());
		turnEndInitializer.initializeState(game);
		assertEquals(State.TURN_START, game.getState());
		assertEquals("Stan", game.getActivePlayer().getName());
        assertEquals(3, game.getActivePlayer().getHand().size());
		assertEquals(EnumSet.of(_9_OF_HEARTS, QUEEN_OF_DIAMONDS, QUEEN_OF_CLUBS), game.getActivePlayer().getHand());
	}
	
	@Test
	public void testEndTurnJack() {
		game.setCardHolder(getCustomCardHolder());
		game.getActivePlayer().getActiveState().setCurrentTurnCards(EnumSet.of(_6_OF_HEARTS, JACK_OF_SPADES));
		assertEquals("Kyle", game.getActivePlayer().getName());
		turnEndInitializer.initializeState(game);
		assertEquals(State.DEMAND_SUIT, game.getState());
		assertEquals("Kyle", game.getActivePlayer().getName());
	}
	
	@Test
	public void testEndTurnEmptyHand() {
		game.setCardHolder(getCustomCardHolder());
		game.getActivePlayer().getActiveState().setCurrentTurnCards(EnumSet.of(_6_OF_HEARTS, JACK_OF_SPADES));
		game.getActivePlayer().setHand(EnumSet.noneOf(Card.class));
		assertEquals("Kyle", game.getActivePlayer().getName());
		turnEndInitializer.initializeState(game);
		assertEquals(State.DEAL_END, game.getState());
		assertEquals("Kyle", game.getActivePlayer().getName());
	}
	
	private void initOneCardInHand(Game game) {
		List<Player> players = game.getPlayers();
		players.get(0).setHand(EnumSet.of(_9_OF_CLUBS));
		players.get(1).setHand(EnumSet.of(_9_OF_HEARTS));
		players.get(2).setHand(EnumSet.of(_9_OF_SPADES));
	}
	
	private CardHolder getCustomCardHolder() {
		CardHolder cardHolder = new CardHolder();
		List<Card> cards = new LinkedList<Card>(Arrays.asList(_6_OF_CLUBS, QUEEN_OF_DIAMONDS, QUEEN_OF_CLUBS, KING_OF_CLUBS, 
				JACK_OF_DIAMONDS, QUEEN_OF_SPADES));
		cardHolder.setCardDeck(new CardDeck(cards));
		return cardHolder;
	}

}
