package com.valeriisosliuk.util;

import java.util.Set;

import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Rank;

public class CardUtil {
	public static long countRank(Set<Card> cards, Rank rank) {
		return cards.stream().filter(c -> c.getRank() == rank).count();
	}
}
