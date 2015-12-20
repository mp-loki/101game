package com.valeriisosliuk.game.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.model.PlayerHolder;

public class PlayerHolderTest {
	
	@Test
	public void testPlayerHolder() {
		PlayerHolder playerHolder = new PlayerHolder();
		assertNotNull(playerHolder.joinGame("Kyle") != null);
		assertNotNull(playerHolder.joinGame("Stan"));
		assertNotNull(playerHolder.joinGame("Cartman"));
		assertNotNull(playerHolder.joinGame("Kenny"));
		assertNotNull(playerHolder.joinGame("Kyle"));
		assertNull(playerHolder.joinGame("Wendy"));
		Player firstPlayer = playerHolder.getActivePlayer();
		assertEquals("Kyle", firstPlayer.getName());
		Player secondPlayer = playerHolder.getNextActivePlayer();
		assertEquals("Stan", secondPlayer.getName());
	}
	
	@Test
	public void testSkipPlayer() {
		PlayerHolder playerHolder = new PlayerHolder();
		assertNotNull(playerHolder.joinGame("Kyle"));
		assertNotNull(playerHolder.joinGame("Stan"));
		assertNotNull(playerHolder.joinGame("Cartman"));
		Player firstPlayer = playerHolder.getActivePlayer();
		assertEquals("Kyle", firstPlayer.getName());
		Player skippedPlayer = playerHolder.skipPlayer();
		assertEquals("Stan", skippedPlayer.getName());
		Player nextActivePlayer = playerHolder.getNextActivePlayer();
		assertEquals("Cartman", nextActivePlayer.getName());
	}
	
	@Test
	public void testSGetSequencedTwoPlayers() {
		PlayerHolder playerHolder = new PlayerHolder();
		assertNotNull(playerHolder.joinGame("Kyle"));
		assertNotNull(playerHolder.joinGame("Stan"));
		
		List<Player> players = playerHolder.getPlayers();
		List<Player> sequencedPlayersKyle = playerHolder.getSequencedPlayers(players.get(0));
		assertEquals(1, sequencedPlayersKyle.size());
		assertEquals("Stan", sequencedPlayersKyle.get(0).getName());
		List<Player> sequencedPlayersStan = playerHolder.getSequencedPlayers(players.get(1));
		assertEquals(1, sequencedPlayersStan.size());
		assertEquals("Kyle", sequencedPlayersStan.get(0).getName());
	}
	
	@Test
	public void testSGetSequencedThreePlayers() {
		PlayerHolder playerHolder = new PlayerHolder();
		assertNotNull(playerHolder.joinGame("Kyle"));
		assertNotNull(playerHolder.joinGame("Stan"));
		assertNotNull(playerHolder.joinGame("Cartman"));
		
		List<Player> players = playerHolder.getPlayers();
		List<Player> sequencedPlayersKyle = playerHolder.getSequencedPlayers(players.get(0));
		assertEquals(2, sequencedPlayersKyle.size());
		assertEquals("Stan", sequencedPlayersKyle.get(0).getName());
		assertEquals("Cartman", sequencedPlayersKyle.get(1).getName());
		
		List<Player> sequencedPlayersStan = playerHolder.getSequencedPlayers(players.get(1));
		assertEquals(2, sequencedPlayersStan.size());
		assertEquals("Cartman", sequencedPlayersStan.get(0).getName());
		assertEquals("Kyle", sequencedPlayersStan.get(1).getName());
		
		List<Player> sequencedPlayersCartman = playerHolder.getSequencedPlayers(players.get(2));
		assertEquals(2, sequencedPlayersCartman.size());
		assertEquals("Kyle", sequencedPlayersCartman.get(0).getName());
		assertEquals("Stan", sequencedPlayersCartman.get(1).getName());
	}
	

}
