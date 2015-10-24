package com.valeriisosliuk.service.handler.validator;

import org.junit.Test;

import static com.valeriisosliuk.model.Card.*;
import static org.junit.Assert.*;

public class SixValidatorTest {
	
	private Validator validator = new SixValidator();
	
	@Test
	public void testTurnOverSixCardPositive() {
		assertTrue(validator.validate(_10_OF_HEARTS, _6_OF_HEARTS));
		assertTrue(validator.validate(_6_OF_SPADES, _6_OF_HEARTS));
		assertTrue(validator.validate(JACK_OF_SPADES, _6_OF_HEARTS));
	}
	
	@Test
	public void testTurnOverSixCardNegative() {
		assertFalse(validator.validate(_10_OF_HEARTS, _6_OF_CLUBS));
	}
}
