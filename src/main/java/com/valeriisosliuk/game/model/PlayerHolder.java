package com.valeriisosliuk.game.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Iterators;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.observer.PlayerObserver;

public class PlayerHolder {
	private static final Logger log = Logger.getLogger(PlayerHolder.class);

	private Player activePlayer;
	private List<Player> players;
	private Iterator<Player> playerIterator;

	public PlayerHolder() {
		players = new LinkedList<>();
	}
	
	public int getPlayersCount() {
		return players.size();
	}
	
	public boolean isPlayerAtTheTable(String playerName) {
		return players.stream().allMatch(p -> p.getName().equals(playerName));
	}
	
	public boolean joinGame(String playerName) {
		if (players.stream().anyMatch(p -> p.getName().equals(playerName))) {
			log.info("Player " + playerName + " is already in game!");
			return true;
		} else if (players.size() <= Game.MAX_PLAYERS) {
		    Player player = new Player(playerName);
		    player.addObserver(new PlayerObserver());
			players.add(player);
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
	 * @return 
	 * 
	 * @return skipped player
	 */
	public Player skipPlayer() {
		if (activePlayer != null) {
			activePlayer.deactivate();
		}
		Player skippedPlayer = getPlayerIterator().next();
		skippedPlayer.skip();
		return skippedPlayer;
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
	/**
	 * Resets iterator to current player. This method is called at the end of a deal to make sure that new deal starts from deal winner
	 * @param dealWinner <code>Player</code> to set 
	 */
    public void resetIterator(Player dealWinner) {
        if (!players.contains(dealWinner)) {
            throw new IllegalArgumentException("Player " + dealWinner.getName() + " is not in this game!");
        }
        while (!playerIterator.next().getName().equals(dealWinner.getName())) {
        }
        
    }

}
