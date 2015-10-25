package com.valeriisosliuk.model;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

public class HandTest {

	@Test
	public void testEmptyHand() {
		Hand hand = new Hand();
		assertEquals(0, hand.getPoints());
	}

	@Test
	public void testHandNoPoints() {
		Set<Card> cards = EnumSet.noneOf(Card.class);
		cards.add(Card._6_OF_SPADES);
		cards.add(Card._7_OF_HEARTS);
		cards.add(Card._8_OF_CLUBS);
		cards.add(Card._9_OF_DIAMONDS);
		Hand hand = new Hand(cards);
		assertEquals(0, hand.getPoints());
	}

	@Test
	public void testShapeOfMyHeart() {
		Set<Card> cards = EnumSet.noneOf(Card.class);
		// He may play the Jack of diamonds
		cards.add(Card.JACK_OF_DIAMONDS);
		// He may lay the Queen of spades
		cards.add(Card.QUEEN_OF_SPADES);
		// He may conceal a King in his hand
		cards.add(Card.KING_OF_HEARTS);
		Hand hand = new Hand(cards);
		// While the memory of it fades
		assertEquals(40, hand.getPoints());
	}

	@Test
	public void testAddCard() {
		Hand hand = new Hand(Arrays.asList(Card._6_OF_HEARTS));
		assertEquals(0, hand.getPoints());
		hand.add(Card.ACE_OF_SPADES);
		assertEquals(15, hand.getPoints());
	}

	@Test
	public void testAddCards() {
		Hand hand = new Hand(Arrays.asList(Card._10_OF_DIAMONDS));
		assertEquals(10, hand.getPoints());
		hand.add(Arrays.asList(Card.ACE_OF_SPADES, Card.JACK_OF_CLUBS));
		assertEquals(45, hand.getPoints());
	}

	@Test
	public void testRemoveCard() {
		Hand hand = new Hand(Arrays.asList(Card.ACE_OF_CLUBS, Card.QUEEN_OF_SPADES, 
				Card.QUEEN_OF_HEARTS, Card.KING_OF_CLUBS));
		assertEquals(45, hand.getPoints());
		hand.remove(Arrays.asList(Card.ACE_OF_CLUBS));
		assertEquals(30, hand.getPoints());
	}
	
	@Test
	public void testRemoveCards() {
		Hand hand = new Hand(Arrays.asList(Card.QUEEN_OF_CLUBS, Card.QUEEN_OF_SPADES, 
				Card.QUEEN_OF_HEARTS, Card.KING_OF_CLUBS));
		assertEquals(40, hand.getPoints());
		hand.remove(Arrays.asList(Card.QUEEN_OF_CLUBS, Card.QUEEN_OF_SPADES, 
				Card.QUEEN_OF_HEARTS));
		assertEquals(10, hand.getPoints());
	}

}
