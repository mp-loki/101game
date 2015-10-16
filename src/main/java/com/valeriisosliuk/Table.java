package com.valeriisosliuk;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.model.Discard;
import com.valeriisosliuk.util.Shuffle;

public class Table {

	public static final int MAX_PLAYERS_AT_THE_TABLE = 4;
	public static final int MIN_PLAYERS_AT_THE_TABLE = 2;

	private CardDeck cardDeck;
	private Discard discard;
	private int id;
	private boolean started;

	private LinkedList<String> players;

	public Table(int id) {
		this.id = id;
		List<Card> allCards = Arrays.asList(Card.values());
		cardDeck = new CardDeck(Shuffle.shuffle(allCards));
		discard = new Discard();
		players = new LinkedList<>();
		started = false;
	}

	public boolean joinTable(String player) {
		int numberOfPlayers = players.size();
		if (numberOfPlayers < MAX_PLAYERS_AT_THE_TABLE && !players.contains(player)) {
			players.add(player);
			return true;
		} else {
			return false;
		}
	}

	public boolean start() {
		if (players.size() >= MIN_PLAYERS_AT_THE_TABLE) {
			started = true;
		} else {
			started = false;
		}
		return started;
	}
	
	public boolean isPlayerAtTheTable(String player) {
		return players.contains(player);
	}
	
	public List<String> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	public void stop(String name) {
		players.remove(name);
		started = false;
	}

	public int getId() {
		return id;
	}
}
