package com.valeriisosliuk.service.handler.validator;

import org.junit.Test;

import static com.valeriisosliuk.model.Card.*;
import static org.junit.Assert.*;

public class FirstCardValidatorTest {
	
	private Validator validator = new FirstCardValidator();
	
	@Test
	public void testFirstCardInTurnPositive() {
		assertTrue(validator.validate(KING_OF_HEARTS, KING_OF_SPADES));
		assertTrue(validator.validate(QUEEN_OF_HEARTS, _10_OF_HEARTS));
		assertTrue(validator.validate(JACK_OF_DIAMONDS, _9_OF_CLUBS));
	}
	
	@Test
	public void testFirstCardInTurnNegative() {
		assertFalse(validator.validate(_7_OF_CLUBS, KING_OF_SPADES));
	}

}
