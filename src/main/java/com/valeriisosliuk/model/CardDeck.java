package com.valeriisosliuk.model;

import java.util.List;
import java.util.Optional;

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
}
