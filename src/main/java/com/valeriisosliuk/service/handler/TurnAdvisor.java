package com.valeriisosliuk.service.handler;

import java.util.EnumSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.service.handler.validator.Validator;
import com.valeriisosliuk.service.handler.validator.ValidatorSupplier;

@Component
public class TurnAdvisor {
	
	@Autowired
	private ValidatorSupplier validatorSupplier;

	public Set<Card> getValidCardsForTurn(Set<Card> hand, Card lastCard, boolean firstMove) {
		EnumSet<Card> validTurns = EnumSet.noneOf(Card.class);
		Validator validator = validatorSupplier.getValidator(lastCard, firstMove);
		for (Card card : hand) {
			if (validator.validate(card, lastCard)) {
				validTurns.add(card);
			}
		}
		return validTurns;
	}
}
