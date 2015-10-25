package com.valeriisosliuk.model;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class Hand {

	private Set<Card> cards;

	public Hand() {
		cards = EnumSet.noneOf(Card.class);
	}

	public Hand(Collection<Card> cards) {
		this();
		this.cards.addAll(cards);
	}

	public void add(Card card) {
		this.cards.add(card);
	}
	
	public void add(Collection<Card> cards) {
		this.cards.addAll(cards);
	}

	public void remove(Card card) {
		this.cards.remove(card);
	}
	public void remove(Collection<Card> cards) {
		this.cards.removeAll(cards);
	}

	public boolean isEmpty() {
		return cards.size() == 0;
	}

	public int getPoints() {
		return cards.stream().mapToInt(c -> c.getRank().getPoints()).sum();
	}
	
	public Set<Card> getCards() {
		return Collections.unmodifiableSet(cards);
	}
}
