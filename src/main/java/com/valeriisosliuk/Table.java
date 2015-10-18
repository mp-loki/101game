package com.valeriisosliuk;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.model.Discard;
import com.valeriisosliuk.model.GameState;
import com.valeriisosliuk.util.Shuffle;

public class Table {

	public static final int MAX_PLAYERS_AT_THE_TABLE = 4;
	public static final int MIN_PLAYERS_AT_THE_TABLE = 2;

	private CardDeck cardDeck;
	private Discard discard;
	private int id;
	private boolean started;

	private Map<String, GameState> players;

	public Table(int id) {
		this.id = id;
		List<Card> allCards = Arrays.asList(Card.values());
		cardDeck = new CardDeck(Shuffle.shuffle(allCards));
		discard = new Discard();
		players = new HashMap<>();
		started = false;
	}

	public boolean joinTable(String player) {
		int numberOfPlayers = players.size();
		if (numberOfPlayers < MAX_PLAYERS_AT_THE_TABLE && !players.keySet().contains(player)) {
			players.put(player, new GameState());
			return true;
		} else {
			return false;
		}
	}

	public boolean start(String userName) {
		GameState state = players.get(userName);
		state.setReady(true);
		state.setHand(cardDeck.getInitialHand());
		if (players.size() >= MIN_PLAYERS_AT_THE_TABLE && 
				players.values().stream().allMatch(GameState::isReady)) {
			started = true;
		} else {
			started = false;
		}
		return started;
	}
	
	public Set<Card> getPlayersHandCards(String username) {
		return players.get(username).getHand().getCards();
	}
	
	public boolean isPlayerAtTheTable(String player) {
		return players.keySet().contains(player);
	}
	
	public Set<String> getPlayers() {
		return Collections.unmodifiableSet(players.keySet());
	}

	public void stop(String name) {
		players.remove(name);
		started = false;
	}

	public int getId() {
		return id;
	}
}
