package com.valeriisosliuk.dto;

import java.util.Set;

import com.valeriisosliuk.model.Card;

public class PlayerStateDto {
    
    private final String name;
    private final Set<Card> hand;
    private final ActiveStateDto active; 

    public PlayerStateDto(String name, Set<Card> hand, ActiveStateDto active) {
        super();
        this.name = name;
        this.hand = hand;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public Set<Card> getHand() {
        return hand;
    }

    public ActiveStateDto getActive() {
        return active;
    }
}
