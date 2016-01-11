package com.valeriisosliuk.game.turnadvisor;

import java.util.EnumSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.valeriisosliuk.game.model.Card;
import com.valeriisosliuk.game.turnadvisor.TurnAdvisor;

import static com.valeriisosliuk.game.model.Card.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.game.Application.class, loader=AnnotationConfigContextLoader.class)
public class TurnAdvisorTest {
	
	@Autowired
	private TurnAdvisor turnAdvisor;
	
	@Test
	public void testSixAdvice() {
		Set<Card> hand = EnumSet.of(KING_OF_CLUBS, _9_OF_CLUBS, _7_OF_HEARTS, _10_OF_DIAMONDS, _10_OF_SPADES, JACK_OF_SPADES);
		Set<Card> validTurns1 = turnAdvisor.getValidCardsForTurn(hand, _6_OF_CLUBS, true);
		assertEquals(EnumSet.of(KING_OF_CLUBS, _9_OF_CLUBS, JACK_OF_SPADES), validTurns1);
		Set<Card> validTurns2 = turnAdvisor.getValidCardsForTurn(hand, _6_OF_CLUBS, false);
		assertEquals(EnumSet.of(KING_OF_CLUBS, _9_OF_CLUBS, JACK_OF_SPADES), validTurns2);
	}
	@Test
	public void testFirstTurnAdvice() {
		Set<Card> hand = EnumSet.of(KING_OF_CLUBS, _9_OF_CLUBS, _7_OF_HEARTS, _10_OF_DIAMONDS, _10_OF_SPADES, JACK_OF_SPADES);
		Set<Card> validTurns = turnAdvisor.getValidCardsForTurn(hand, _10_OF_HEARTS, true);
		assertEquals(EnumSet.of(_10_OF_DIAMONDS, _10_OF_SPADES, _7_OF_HEARTS, JACK_OF_SPADES), validTurns);
	}
	@Test
	public void testSecondTurnAdvice() {
		Set<Card> hand = EnumSet.of(KING_OF_CLUBS, _9_OF_CLUBS, _7_OF_HEARTS, _10_OF_DIAMONDS, _10_OF_SPADES, JACK_OF_SPADES);
		Set<Card> validTurns = turnAdvisor.getValidCardsForTurn(hand, _10_OF_HEARTS, false);
		assertEquals(EnumSet.of(_10_OF_DIAMONDS, _10_OF_SPADES), validTurns);
	}

}
