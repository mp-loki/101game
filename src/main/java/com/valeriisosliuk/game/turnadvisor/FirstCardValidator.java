package com.valeriisosliuk.game.turnadvisor;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.model.Card;
import com.valeriisosliuk.game.model.Rank;

/**
 * 
 * @author valerii.sosliuk
 * 
 * First card in turn should be either of same rank or suite as last card
 * Jack is a 'wildcard' and it can be put over any card
 *
 */
@Component
public class FirstCardValidator implements Validator {

	@Override
	public boolean validate(Card actionCard, Card lastCard) {
		if (actionCard.getRank() == Rank.JACK) {
			return true;
		}
		if (actionCard.getRank() == lastCard.getRank() || 
				actionCard.getSuit() == lastCard.getSuit()) {
			return true;
		}
		return false;
	}

}
