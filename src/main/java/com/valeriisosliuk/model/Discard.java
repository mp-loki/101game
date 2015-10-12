package com.valeriisosliuk.model;

import java.util.ArrayList;
import java.util.List;

import com.valeriisosliuk.util.Shuffle;

public class Discard {
	
	private static final int MAX_DECK_SIZE = 32;
	private List<Card> discard;
	
	public Discard() {
		discard = new ArrayList<>(MAX_DECK_SIZE);
	}
	
	public void add(Card card) {
		discard.add(card);
	}
	
	public CardDeck turnOver() {
		Shuffle.shuffle(discard);
		return new CardDeck(discard);
	}	

}
