package com.valeriisosliuk.model;

import java.util.Set;

public class GameTurnMessage {
    
    private String player;
    private String message;
    private Set<Card> cards;
    
    public String getPlayer() {
        return player;
    }
    public void setPlayer(String player) {
        this.player = player;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
	public Set<Card> getCards() {
		return cards;
	}
	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
}

