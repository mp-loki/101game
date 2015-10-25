package com.valeriisosliuk.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Iterators;
import com.valeriisosliuk.dto.PlayerDetail;
import com.valeriisosliuk.util.Shuffle;

public class Table {

    public static final int MAX_PLAYERS_AT_THE_TABLE = 4;
    public static final int MIN_PLAYERS_AT_THE_TABLE = 2;

    private CardDeck cardDeck;
    private Discard discard;
    private boolean started;

    private List<Player> players;
    private Iterator<Player> playerIterator;

    private Player activePlayer;

    public Table() {
        List<Card> allCards = Arrays.asList(Card.values());
        cardDeck = new CardDeck(Shuffle.shuffle(allCards));
        discard = new Discard(cardDeck.getNext().get());
        players = new LinkedList<>();
        started = false;
    }

    public boolean joinTable(String player) {
        int numberOfPlayers = players.size();
        if (numberOfPlayers < MAX_PLAYERS_AT_THE_TABLE && !isPlayerAtTheTable(player)) {
            players.add(new Player(player));
            return true;
        } else {
            return false;
        }
    }

    public boolean start(String userName) {
        Player player = getPlayer(userName);
        player.setReady(true);
        player.setHand(cardDeck.getInitialHand());
        if (players.size() >= MIN_PLAYERS_AT_THE_TABLE && players.stream().allMatch(Player::isReady)) {
            getNextActivePlayer();
            started = true;
        } else {
            started = false;
        }
        return started;
    }

    /**
     * Returns a sequence of players which go after current player
     * 
     * @param player
     *            current player
     * @return ordered List of players
     */
    public Map<String, Integer> getSequencedPlayers(String currentPlayer) {
        Map<String, Integer> result = new LinkedHashMap<>();
        Iterator<Player> playerIter = Iterators.cycle(players);
        Player player = null;
        // roll playerIter until current user is met
        while (!playerIter.next().getName().equals(currentPlayer)) {
        }
        // Then add all the rest of users except current user
        while (!((player = playerIter.next()).getName().equals(currentPlayer))) {
            result.put(player.getName(), player.getHandSize());
        }
        return result;
    }

    public List<PlayerDetail> getSequencedPlayersList(String currentPlayer) {
        List<PlayerDetail> result = new LinkedList<>();
        Iterator<Player> playerIter = Iterators.cycle(players);
        Player player = null;
        // roll playerIter until current user is met
        while (!playerIter.next().getName().equals(currentPlayer)) {
        }
        // Then add all the rest of users except current user
        while (!((player = playerIter.next()).getName().equals(currentPlayer))) {
            result.add(new PlayerDetail(player.getName(), player.getHandSize()));
        }
        return result;
    }

    public Map<String, Integer> getCardNumsPerPlayer() {
        return players.stream().collect(Collectors.toMap(Player::getName, Player::getHandSize));
    }

    public Player getPlayer(String name) {
        return players.stream().filter(p -> p.getName().equals(name)).findFirst().get();
    }

    public Set<Card> getPlayersHand(String username) {
        return getPlayer(username).getHand();
    }

    public boolean isPlayerAtTheTable(String player) {
        return players.stream().anyMatch(p -> p.getName().equals(player));
    }

    public List<String> getPlayerNames() {
        return players.stream().map(Player::getName).collect(Collectors.toList());
    }
    public List<Player> getPlayers() {
    	return Collections.unmodifiableList(players);
    }

    public void stop(String name) {
        players.remove(name);
        started = false;
    }

    public boolean cardDeckHasNext() {
        return cardDeck.hasNext();
    }

    public Optional<Card> getCardFromDeck() {
        return cardDeck.getNext();
    }

    public Card getLastCardInDiscard() {
        return discard.getLastCard().orElseThrow(() -> new IllegalStateException("Discard must have at least one card in it"));
    }
    
    public void putCardInDiscard(Card card) {
    	discard.add(card);
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

    public Player getNextActivePlayer() {
    	if (activePlayer != null) {
    		deactivateCurrentPlayer();
    	}
    	activateNextPlayer();
    	return activePlayer;
    }

    private void activateNextPlayer() {
    	activePlayer = getPlayerIterator().next();
    	activePlayer.setPickAllowed(true);
        activePlayer.setEndTurnAllowed(true);
        activePlayer.setFirstMove(true);
	}

	private void deactivateCurrentPlayer() {
		activePlayer.setPickAllowed(false);
        activePlayer.setEndTurnAllowed(false);
        activePlayer.setFirstMove(false);
	}

	public boolean isStarted() {
        return started;
    }

    public boolean isActivePlayer(String player) {
        if (activePlayer == null) {
            return false;
        }
        return activePlayer.getName().equals(player);
    }

    public boolean isActivePlayer(Player player) {
    	return player.equals(activePlayer);
    }
    public void turnOver() {
        if (!cardDeckHasNext()) {
            cardDeck = discard.turnOver();
        }

    }
    /**
     * Advances player iterator without setting a new activePlayer
     * @return skipped player
     */
	public Player skipPlayer() {
		return getPlayerIterator().next();
	}
}
