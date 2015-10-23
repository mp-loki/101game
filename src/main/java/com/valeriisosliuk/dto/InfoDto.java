package com.valeriisosliuk.dto;

import java.util.Map;

import com.valeriisosliuk.model.Card;

public class InfoDto {
    private String message;
    private Card lastCard;
    private boolean deckEmpty;
    private Map<String, Integer> cardsNumPerPlayer;
    private String activePlayer;
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Card getLastCard() {
        return lastCard;
    }
    public void setLastCard(Card lastCard) {
        this.lastCard = lastCard;
    }
    public boolean isDeckEmpty() {
        return deckEmpty;
    }
    public void setDeckEmpty(boolean deckEmpty) {
        this.deckEmpty = deckEmpty;
    }
    public Map<String, Integer> getCardsNumPerPlayer() {
        return cardsNumPerPlayer;
    }
    public void setCardsNumPerPlayer(Map<String, Integer> cardsPerPlayer) {
        this.cardsNumPerPlayer = cardsPerPlayer;
    }
    public String getActivePlayer() {
        return activePlayer;
    }
    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }
}
