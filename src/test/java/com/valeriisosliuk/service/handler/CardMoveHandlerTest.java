package com.valeriisosliuk.service.handler;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.model.Table;
import com.valeriisosliuk.util.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class CardMoveHandlerTest {

	@Autowired
	private CardMoveHandler handler;
	
	@Test
	public void testAceMove() {
		Table table = TestUtil.getTableFourPlayers();
		CardDeck deck = new CardDeck(Arrays.asList(Card.values()));
	}
}
