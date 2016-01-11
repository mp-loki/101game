package com.valeriisosliuk.game.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.valeriisosliuk.game.model.Card;
import com.valeriisosliuk.game.model.CardDeck;
import com.valeriisosliuk.game.model.Rank;

public class CardUtil {
	
	public static long countRank(Set<Card> cards, Rank rank) {
		return cards.stream().filter(c -> c.getRank() == rank).count();
	}
	
	public static CardDeck getShuffledCardDeck() {
		List<Card> allCards = Arrays.asList(Card.values());
        return new CardDeck(Shuffle.shuffle(allCards));
	}
}
