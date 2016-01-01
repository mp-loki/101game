package com.valeriisosliuk.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.model.Rank;

public class CardUtil {
	
	public static long countRank(Set<Card> cards, Rank rank) {
		return cards.stream().filter(c -> c.getRank() == rank).count();
	}
	
	public static CardDeck getShuffledCardDeck() {
		List<Card> allCards = Arrays.asList(Card.values());
        return new CardDeck(Shuffle.shuffle(allCards));
	}
}
