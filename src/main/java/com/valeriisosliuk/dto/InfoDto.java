package com.valeriisosliuk.dto;

import com.valeriisosliuk.model.Card;

public class InfoDto {
    private String message;
    private Card lastCard;
    private boolean deckEmpty;
    
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
    
    
}
