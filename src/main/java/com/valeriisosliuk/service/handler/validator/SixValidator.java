package com.valeriisosliuk.service.handler.validator;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.model.Card;

import static com.valeriisosliuk.model.Rank.*;
/**
 * 
 * @author valerii.sosliuk
 * Six card must be covered either by another Six, same suit card or Jack
 */
@Component
public class SixValidator implements Validator {

	@Override
	public boolean validate(Card actionCard, Card lastCard) {
		if (lastCard.getRank() != _6) {
			return false;
		}
		if (actionCard.getRank() == _6 || actionCard.getRank() == JACK) {
			return true;
		}
		if (actionCard.getSuit() == lastCard.getSuit()) {
			return true;
		}
		return false;
	}

}
