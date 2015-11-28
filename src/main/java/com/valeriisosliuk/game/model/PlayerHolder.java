package com.valeriisosliuk.game.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Iterators;
import com.valeriisosliuk.game.model.Player;

public class PlayerHolder {
	private static final Logger log = Logger.getLogger(PlayerHolder.class);

	public static final int MAX_PLAYERS = 3;
	private Player activePlayer;
	private List<Player> players;
	private Iterator<Player> playerIterator;

	public PlayerHolder() {
		players = new LinkedList<>();
	}

	public boolean joinGame(String playerName) {
		if (players.stream().anyMatch(p -> p.getName().equals(playerName))) {
			log.info("Player " + playerName + " is already in game!");
			return true;
		} else if (players.size() < MAX_PLAYERS) {
			players.add(new Player(playerName));
			return true;
		}
		return false;
	}

	private Iterator<Player> getPlayerIterator() {
		if (playerIterator == null) {
			playerIterator = Iterators.cycle(players);
		}
		return playerIterator;
	}

	public Player getActivePlayer() {
		if (activePlayer == null) {
			getNextActivePlayer();
		}
		return activePlayer;
	}

	/**
	 * Advances player iterator without setting a new activePlayer
	 * 
	 * @return skipped player
	 */
	public Player skipPlayer() {
		return getPlayerIterator().next();
	}

	public Player getNextActivePlayer() {
		if (activePlayer != null) {
			activePlayer.deactivate();
		}
		activePlayer = getPlayerIterator().next();
		activePlayer.activate();
		return activePlayer;
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

}
