package com.valeriisosliuk.game.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.model.PlayerHolder;

public class PlayerHolderTest {
	
	@Test
	public void testPlayerHolder() {
		PlayerHolder playerHolder = new PlayerHolder();
		assertTrue(playerHolder.joinGame("Kyle"));
		assertTrue(playerHolder.joinGame("Stan"));
		assertTrue(playerHolder.joinGame("Cartman"));
		assertTrue(playerHolder.joinGame("Kyle"));
		assertFalse(playerHolder.joinGame("Kenny"));
		Player firstPlayer = playerHolder.getActivePlayer();
		assertEquals("Kyle", firstPlayer.getName());
		Player secondPlayer = playerHolder.getNextActivePlayer();
		assertEquals("Stan", secondPlayer.getName());
	}
	
	@Test
	public void testSkipPlayer() {
		PlayerHolder playerHolder = new PlayerHolder();
		assertTrue(playerHolder.joinGame("Kyle"));
		assertTrue(playerHolder.joinGame("Stan"));
		assertTrue(playerHolder.joinGame("Cartman"));
		Player firstPlayer = playerHolder.getActivePlayer();
		assertEquals("Kyle", firstPlayer.getName());
		Player skippedPlayer = playerHolder.skipPlayer();
		assertEquals("Stan", skippedPlayer.getName());
		Player nextActivePlayer = playerHolder.getNextActivePlayer();
		assertEquals("Cartman", nextActivePlayer.getName());
	}

}
