package com.valeriisosliuk.game.state.initializer;

import static org.junit.Assert.*;

import org.junit.Test;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.initializer.DealStartStateInitializer;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;

public class DealStartStateInitializerTest {
	
	@Test
	public void testStartDeal() {
		Game game = new Game();
		game.joinGame("Kyle");
		game.joinGame("Stan");
		game.joinGame("Cartman");
		
		StateInitinalizer dealStartInitializer = new DealStartStateInitializer();
		dealStartInitializer.initializeState(game);
		assertEquals("Kyle", game.getActivePlayer().getName());
		
		for (Player player : game.getPlayers()) {
			assertEquals(4, player.getHand().size());
		}
		assertTrue(game.getCardHolder().cardDeckHasNext());
		assertNotNull(game.getCardHolder().getLastCardInDiscard());
	}

}
