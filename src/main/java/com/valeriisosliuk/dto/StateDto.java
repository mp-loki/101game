package com.valeriisosliuk.dto;

import java.util.List;
import java.util.Set;

import com.valeriisosliuk.model.Card;

public class StateDto {

    private String currentPlayerName;
    private Card lastCard;
    private Set<Card> hand;
    private List<PlayerDetail> players;
    private String message;
    private boolean pickAllowed;
    
    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public List<PlayerDetail> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDetail> players) {
        this.players = players;
    }

    public void setCurrentPlayerName(String currentPlayerName) {
        this.currentPlayerName = currentPlayerName;
    }

    public Card getLastCard() {
        return lastCard;
    }

    public void setLastCard(Card lastCard) {
        this.lastCard = lastCard;
    }

    public Set<Card> getHand() {
        return hand;
    }

    public void setHand(Set<Card> hand) {
        this.hand = hand;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPickAllowed() {
        return pickAllowed;
    }

    public void setPickAllowed(boolean pickAllowed) {
        this.pickAllowed = pickAllowed;
    }
}
