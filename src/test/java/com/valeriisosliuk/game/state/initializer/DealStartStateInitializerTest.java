package com.valeriisosliuk.game.state.initializer;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.game.Application.class, loader=AnnotationConfigContextLoader.class)
public class DealStartStateInitializerTest {
	
	@Resource
	StateInitinalizer dealStartStateInitializer;
	
	@Test
	public void testStartDeal() {
		Game game = new Game();
		game.joinGame("Kyle");
		game.joinGame("Stan");
		game.joinGame("Cartman");
		
		dealStartStateInitializer.initializeState(game);
		assertEquals("Kyle", game.getActivePlayer().getName());
		
		for (Player player : game.getPlayers()) {
			assertEquals(4, player.getHand().size());
		}
		assertTrue(game.getCardHolder().cardDeckHasNext());
		assertNotNull(game.getCardHolder().getLastCardInDiscard());
	}

}
