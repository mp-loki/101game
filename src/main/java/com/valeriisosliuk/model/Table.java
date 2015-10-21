package com.valeriisosliuk.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Iterators;
import com.valeriisosliuk.util.Shuffle;

public class Table {

    public static final int MAX_PLAYERS_AT_THE_TABLE = 4;
    public static final int MIN_PLAYERS_AT_THE_TABLE = 2;

    private CardDeck cardDeck;
    private Discard discard;
    private int id;
    private boolean started;

    private List<Player> players;
    private Iterator<Player> playerIterator;

    private Player activePlayer;

    public Table(int id) {
        this.id = id;
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
        Player state = getPlayer(userName);
        state.setReady(true);
        state.setHand(cardDeck.getInitialHand());
        if (players.size() >= MIN_PLAYERS_AT_THE_TABLE && players.stream().allMatch(Player::isReady)) {
            started = true;
        } else {
            started = false;
        }
        return started;
    }

    private Player getPlayer(String name) {
        return players.stream().filter(p -> p.getName().equals(name)).findFirst().get();
    }

    public Set<Card> getPlayersHandCards(String username) {
        return getPlayer(username).getHand().getCards();
    }

    public boolean isPlayerAtTheTable(String player) {
        return players.stream().anyMatch(p -> p.getName().equals(player));
    }

    public List<String> getPlayers() {
        return players.stream().map(Player::getName).collect(Collectors.toList());
    }

    public void stop(String name) {
        players.remove(name);
        started = false;
    }

    public int getId() {
        return id;
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

    private Iterator<Player> getPlayerIterator() {
        if (playerIterator == null) {
            playerIterator = Iterators.cycle(players);
        }
        return playerIterator;
    }

    public Player getActivePlayer() {
        if (activePlayer == null) {
            this.activePlayer = getPlayerIterator().next();
        }
        return activePlayer;
    }

    public Player getNextActivePlayer() {
        this.activePlayer = getPlayerIterator().next();
        return activePlayer;
    }
}