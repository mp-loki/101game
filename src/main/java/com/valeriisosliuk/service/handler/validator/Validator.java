package com.valeriisosliuk.service.handler.validator;

import com.valeriisosliuk.model.Card;

public interface Validator {
	/**
	 * chacks if a move of actionCard on lastCard is valid
	 * @param actionCard card to check
	 * @param lastCard last card in discard
	 * @return true, if moving actionCard over lastCard is a valid turn, false otherwise
	 */
	boolean validate(Card actionCard, Card lastCard);
}
