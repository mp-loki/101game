package com.valeriisosliuk.game.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Iterators;
import com.valeriisosliuk.game.observer.AbstractObservable;

public class PlayerHolder extends AbstractObservable {

	private Player activePlayer;
	private List<Player> players;
	private Iterator<Player> playerIterator;

	public PlayerHolder() {
		players = new LinkedList<>();
	}

	public int getPlayersCount() {
		return players.size();
	}

	public boolean isPlayerInGame(String playerName) {
		return players.stream().anyMatch(p -> p.getName().equals(playerName));
	}

	public Player joinGame(String playerName) {
		return players.stream().filter(p -> p.getName().equals(playerName)).findFirst()
				.orElse(createNewPlayer(playerName));
	}

	private Player createNewPlayer(String playerName) {
		if (players.size() < Game.MAX_PLAYERS) {
			Player player = new Player(playerName);
			players.add(player);
			setChangedAndNotify();
			return player;
		} else {
			return null;
		}
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
		if (activePlayer.getActiveState() == null) {
		    activePlayer.activate();
		}
		return activePlayer;
	}

	/**
	 * Advances player iterator without setting a new activePlayer
	 * 
	 * @return
	 * 
	 * @return skipped player
	 */
	public Player skipPlayer() {
		if (activePlayer != null) {
			activePlayer.skip();
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

	public List<Player> getSequencedPlayers(Player currentPlayer) {
		if (!players.contains(currentPlayer)) {
			throw new IllegalArgumentException("Player is not from this game!");
		}
		List<Player> result = new LinkedList<Player>();
		Iterator<Player> playerIter = Iterators.cycle(players);
		while (!playerIter.next().equals(currentPlayer)) {
		}
		Player player = null;
		while (!((player = playerIter.next()).equals(currentPlayer))) {
			result.add(player);
		}
		return result;
	}
	
	public List<Player> getSequencedPlayers(String playerName) {
	   Player player = getPlayer(playerName).get();
	   return getSequencedPlayers(player);
	}

	/**
	 * Resets iterator to current player. This method is called at the end of a
	 * deal to make sure that new deal starts from deal winner
	 * 
	 * @param dealWinner
	 *            <code>Player</code> to set
	 */
	public void resetIterator(Player dealWinner) {
		if (!players.contains(dealWinner)) {
			throw new IllegalArgumentException("Player " + dealWinner.getName() + " is not in this game!");
		}
		Player player = null;
		while (!(player = playerIterator.next()).getName().equals(dealWinner.getName())) {
		}
		activePlayer = player;
		activePlayer.nullifyActiveState();
		//activePlayer.activate();
		
	}
	
	   
    public Optional<Player> getPlayer(String name) {
        return players.stream().filter(p -> p.getName().equals(name)).findFirst();
    }

    public boolean isActive(Player player) {
        return player.equals(activePlayer);
    }

    public String getWinner() {
        // Technically it's possible that multiple players have same minimal amount of points, in this case both are considered winners
        // TODO: implement returninng list of names in the case described above
        return players.stream().min((p1, p2) -> Integer.compare(p1.getTotalPoints(), p2.getTotalPoints())).get().getName();
    }

}
