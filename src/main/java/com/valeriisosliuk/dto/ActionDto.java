package com.valeriisosliuk.dto;

import java.util.List;
import java.util.Set;

import com.valeriisosliuk.model.Card;

public class ActionDto {

    private String currentPlayer;
    private List<String> otherPlayers;
    private String message;
    private Set<Card> hand;
    private Card lead;

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<String> getOtherPlayers() {
        return otherPlayers;
    }

    public void setOtherPlayers(List<String> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<Card> getHand() {
        return hand;
    }

    public void setHand(Set<Card> hand) {
        this.hand = hand;
    }

    public Card getLead() {
        return lead;
    }

    public void setLead(Card lead) {
        this.lead = lead;
    }

}
