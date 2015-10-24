package com.valeriisosliuk.service.handler.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Rank;

@Component
public class ValidatorSupplier {
	
	@Autowired
	private FirstCardValidator firstCardValidator;
	
	@Autowired
	private SubsequentCardValidator subsequentCardValidator;
	
	@Autowired
	private SixValidator sixValidator;
	
	public Validator getValidator(Card lastCard, boolean firstMove) {
		if (lastCard.getRank() == Rank._6) {
			return sixValidator;
		} else if (firstMove) {
			return firstCardValidator;
		} else {
			return subsequentCardValidator;
		}
	}

}
