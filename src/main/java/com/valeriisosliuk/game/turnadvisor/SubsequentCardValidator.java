package com.valeriisosliuk.game.turnadvisor;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.model.Card;
/**
 * 
 * @author valerii.sosliuk
 * 
 * Subsequent cards in turn should be of same rank only.
 * Same suit or 'wildcard' Jack are not allowed
 *
 */
@Component
public class SubsequentCardValidator implements Validator {

	@Override
	public boolean validate(Card actionCard, Card lastCard) {
		return actionCard.getRank() == lastCard.getRank();
	}

}
