package com.valeriisosliuk.game.state.actionhandler;

import static com.valeriisosliuk.model.Card.ACE_OF_CLUBS;
import static com.valeriisosliuk.model.Card.ACE_OF_DIAMONDS;
import static com.valeriisosliuk.model.Card.KING_OF_CLUBS;
import static com.valeriisosliuk.model.Card._10_OF_CLUBS;
import static com.valeriisosliuk.model.Card._6_OF_CLUBS;
import static com.valeriisosliuk.model.Card._7_OF_CLUBS;
import static com.valeriisosliuk.model.Card._7_OF_DIAMONDS;
import static com.valeriisosliuk.model.Card._8_OF_CLUBS;
import static com.valeriisosliuk.model.Card._8_OF_HEARTS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class PickActionHandlerTest {
	
	@Autowired
	private PickActionHandler pickActionHandler;
	
	private Game game;
	
	@Before
	public void setUp() {
		game = new Game();
        game.joinGame("Kyle");
        game.setState(State.TURN_START);
        List<Card> cards = new ArrayList<>(Arrays.asList(Card.values()));
        CardDeck cardDeck = new CardDeck(cards);
        game.getActivePlayer().setHand(cardDeck.getInitialHand());
        CardHolder holder = new CardHolder();
        game.setCardHolder(holder);
        holder.setCardDeck(cardDeck);
	}
	
	@Test
	public void testPickCard() {
		Action action = new Action(ActionType.PICK, "Kyle");
		
		assertEquals(4, game.getActivePlayer().getHand().size());
		Set<Card> cardsInHand = new HashSet<Card>(Arrays.asList(ACE_OF_CLUBS, _6_OF_CLUBS, _7_OF_CLUBS, _8_OF_CLUBS));
		assertEquals(cardsInHand, game.getActivePlayer().getHand());
		
		assertTrue(game.getCardHolder().cardDeckHasNext());
		State nextState = pickActionHandler.handleAction(game, action);
		assertEquals(State.TURN_START, nextState);
		assertEquals(5, game.getActivePlayer().getHand().size());
		assertFalse("Pick is allowed when it should not be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertTrue("Pass is not when it should be", game.getActivePlayer().getActiveState().isPassAllowed());
		cardsInHand = new HashSet<Card>(Arrays.asList(ACE_OF_CLUBS, _6_OF_CLUBS, _7_OF_CLUBS, _8_OF_CLUBS, _10_OF_CLUBS));
		assertEquals(cardsInHand, game.getActivePlayer().getHand());
	}
	
	@Test
	public void testPickCardNegative() {
		game.setState(State.TURN_IN_PROGRESS);
		game.getActivePlayer().getActiveState().setPickAllowed(false);;
		Action action = new Action(ActionType.PICK, "Kyle");
		
		assertEquals(4, game.getActivePlayer().getHand().size());
		Set<Card> cardsInHand = new HashSet<Card>(Arrays.asList(ACE_OF_CLUBS, _6_OF_CLUBS, _7_OF_CLUBS, _8_OF_CLUBS));
		assertEquals(cardsInHand, game.getActivePlayer().getHand());
		
		assertTrue(game.getCardHolder().cardDeckHasNext());
		State nextState = pickActionHandler.handleAction(game, action);
		assertEquals(State.TURN_IN_PROGRESS, nextState);
		assertEquals(4, game.getActivePlayer().getHand().size());
		assertFalse("Pick is allowed when it should not be", game.getActivePlayer().getActiveState().isPickAllowed());
		assertEquals(new HashSet<Card>(Arrays.asList(ACE_OF_CLUBS, _6_OF_CLUBS, _7_OF_CLUBS, _8_OF_CLUBS)), game.getActivePlayer().getHand());
	}
	
	@Test
	public void testPickCardSixInDiscard() {
		game.setCardHolder(getCustomCardHolder());
		game.setState(State.TURN_IN_PROGRESS);
		assertEquals(_6_OF_CLUBS, game.getCardHolder().getLastCardInDiscard());
		game.getActivePlayer().setHand(new HashSet<Card>(Arrays.asList(ACE_OF_DIAMONDS)));
		
		Action firstAction = new Action(ActionType.PICK, "Kyle");
		assertEquals(State.TURN_IN_PROGRESS, pickActionHandler.handleAction(game, firstAction));
		assertEquals(new HashSet<Card>(Arrays.asList(ACE_OF_DIAMONDS, _7_OF_DIAMONDS)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<Card>(), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		
		Action secondAction = new Action(ActionType.PICK, "Kyle");
		assertEquals(State.TURN_IN_PROGRESS, pickActionHandler.handleAction(game, secondAction));
		assertEquals(new HashSet<Card>(Arrays.asList(ACE_OF_DIAMONDS, _7_OF_DIAMONDS, _8_OF_HEARTS)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<Card>(), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
		
		Action thirdAction = new Action(ActionType.PICK, "Kyle");
		assertEquals(State.TURN_IN_PROGRESS, pickActionHandler.handleAction(game, thirdAction));
		assertEquals(new HashSet<Card>(Arrays.asList(ACE_OF_DIAMONDS, _7_OF_DIAMONDS, _8_OF_HEARTS, KING_OF_CLUBS)), game.getActivePlayer().getHand());
		assertEquals(new HashSet<Card>(Arrays.asList(KING_OF_CLUBS)), game.getActivePlayer().getActiveState().getTurnOptions());
		assertTrue("Pick is not allowed when it should be", game.getActivePlayer().getActiveState().isPickAllowed());
	}
	
	private CardHolder getCustomCardHolder() {
		CardHolder cardHolder = new CardHolder();
		List<Card> cards = new LinkedList<Card>(Arrays.asList(_6_OF_CLUBS, _7_OF_DIAMONDS, _8_OF_HEARTS, KING_OF_CLUBS));
		cardHolder.setCardDeck(new CardDeck(cards));
		return cardHolder;
	}
}
