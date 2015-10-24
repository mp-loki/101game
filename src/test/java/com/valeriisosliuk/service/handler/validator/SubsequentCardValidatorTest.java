package com.valeriisosliuk.service.handler.validator;

import org.junit.Test;


import static com.valeriisosliuk.model.Card.*;
import static org.junit.Assert.*;

public class SubsequentCardValidatorTest {

	private Validator validator = new SubsequentCardValidator();
	
	@Test
	public void testSubsequentCardPositive() {
		assertTrue(validator.validate(ACE_OF_DIAMONDS, ACE_OF_SPADES));
	}
	@Test
	public void testSubsequentCardNegative() {
		assertFalse(validator.validate(ACE_OF_DIAMONDS, QUEEN_OF_CLUBS));
		assertFalse(validator.validate(ACE_OF_DIAMONDS, _10_OF_DIAMONDS));
		assertFalse(validator.validate(JACK_OF_DIAMONDS, QUEEN_OF_CLUBS));
	}
}
