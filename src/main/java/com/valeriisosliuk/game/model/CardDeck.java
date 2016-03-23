package com.valeriisosliuk.game.model;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CardDeck {
	
	private List<Card> cards;
	
	public CardDeck(List<Card> cards) {
		this.cards = cards;
	}
	
	public Optional<Card> getNext() {
		if (cards.size() > 0) {
			return Optional.of(cards.remove(0));
		} else {
			return Optional.empty();
		}
	}
	
	public boolean hasNext() {
	    return cards.size() > 0;
	}

	public Set<Card> getInitialHand() {
		List<Card> cardsToHand = cards.subList(0, 4);
		Set<Card> hand = EnumSet.noneOf(Card.class);
		hand.addAll(cardsToHand);
		cards.removeAll(cardsToHand);
		return hand;
	}
}
