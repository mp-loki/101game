package com.valeriisosliuk;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.model.Discard;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Rank;
import com.valeriisosliuk.model.Suit;
import com.valeriisosliuk.util.Shuffle;

public class Table {
	
	private CardDeck cardDeck;
	private Discard discard;
	
	private LinkedHashMap<String, Player> players;
	
	public void init() {
		List<Card> allCards = Arrays.asList(Card.values());
		cardDeck = new CardDeck(Shuffle.shuffle(allCards));
		discard = new Discard();
	}
	
}
