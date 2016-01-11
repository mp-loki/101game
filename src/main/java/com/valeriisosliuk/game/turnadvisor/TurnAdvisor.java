package com.valeriisosliuk.game.turnadvisor;

import java.util.EnumSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.model.Card;
import com.valeriisosliuk.game.model.Suit;

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
	
	public Set<Card> getValidCardsForRespondSuit(Set<Card> hand, Suit suit) {
	    EnumSet<Card> validTurns = EnumSet.noneOf(Card.class);
	    for (Card card : hand) {
	        if (card.getSuit() == suit) {
	            validTurns.add(card);
	        }
	    }
	    return validTurns;
	}
}
