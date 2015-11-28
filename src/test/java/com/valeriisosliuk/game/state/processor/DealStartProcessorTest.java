package com.valeriisosliuk.game.state.processor;

import static org.junit.Assert.*;

import org.junit.Test;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;

public class DealStartProcessorTest {
	
	@Test
	public void testStartDeal() {
		Game game = new Game();
		game.joinGame("Kyle");
		game.joinGame("Stan");
		game.joinGame("Cartman");
		
		StateProcessor dealStartProcessor = new DealStartProcessor();
		dealStartProcessor.initializeState(game);
		assertEquals("Kyle", game.getActivePlayer().getName());
		
		for (Player player : game.getPlayers()) {
			assertEquals(4, player.getHand().size());
		}
		assertTrue(game.getCardHolder().cardDeckHasNext());
		assertNotNull(game.getCardHolder().getLastCardInDiscard());
	}

}
