package com.valeriisosliuk.game.state.actionhandler;

import static org.junit.Assert.*;

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
import com.valeriisosliuk.game.state.processor.DealStartProcessor;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;

import static com.valeriisosliuk.model.Card.*;

import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.model.Discard;

public class PickActionHandlerTest {
	
	PickActionHandler pickActionHandler;
	
	private Game game;
	
	@Before
	public void setUp() {
		pickActionHandler = new PickActionHandler();
		game = new Game();
        game.joinGame("Kyle");
        List<Card> cards = new ArrayList<>(Arrays.asList(Card.values()));
        CardDeck cardDeck = new CardDeck(cards);
        game.getActivePlayer().setHand(cardDeck.getInitialHand());
        CardHolder holder = new CardHolder();
        game.setCardHolder(holder);
        holder.setCardDeck(cardDeck);
	}
	
	
	@Test
	public void testPickCard() {
		Action action = new Action();
		action.setType(ActionType.PICK);
		action.setCurrentPlayer("Kyle");
		
		assertEquals(4, game.getActivePlayer().getHand().size());
		Set<Card> cardsInHand = new HashSet<Card>();
		cardsInHand.addAll(Arrays.asList(ACE_OF_CLUBS, _6_OF_CLUBS, _7_OF_CLUBS, _8_OF_CLUBS));
		assertEquals(cardsInHand, game.getActivePlayer().getHand());
		
		
		assertTrue(game.getCardHolder().cardDeckHasNext());
		assertEquals(Card._9_OF_CLUBS, game.getCardHolder().getLastCardInDiscard());
		State nextState = pickActionHandler.handleAction(game, action);
		assertEquals(State.TURN_IN_PROGRESS, nextState);
		assertEquals(5, game.getActivePlayer().getHand().size());
		cardsInHand.addAll(Arrays.asList(ACE_OF_CLUBS, _6_OF_CLUBS, _7_OF_CLUBS, _8_OF_CLUBS, _10_OF_CLUBS));
		assertEquals(cardsInHand, game.getActivePlayer().getHand());
	}

}
