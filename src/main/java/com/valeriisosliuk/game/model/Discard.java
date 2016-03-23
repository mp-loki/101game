package com.valeriisosliuk.game.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.valeriisosliuk.game.util.Shuffle;

public class Discard {
	
	private List<Card> discard;
	
	public Discard(Card card) {
		discard = new LinkedList<>();
		discard.add(card);
	}
	
	public void add(Card card) {
		discard.add(0, card);
	}
	
	public CardDeck turnOver() {
	    Card lastCard = getLastCard().get();
	    discard.remove(lastCard);
		Shuffle.shuffle(discard);
		CardDeck cardDeck = new CardDeck(discard);
		discard =  new LinkedList<>();
		discard.add(lastCard);
		return cardDeck;
	}	
	
	public Optional<Card> getLastCard() {
	    return discard.size() > 0 ? Optional.of(discard.get(0)) : Optional.empty();
	}
}
