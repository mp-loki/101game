package com.valeriisosliuk.dto;

import java.util.Set;

import com.valeriisosliuk.model.Card;

public class PlayerStateDto {
    
    private final String name;
    private final Set<Card> hand;
    private final ActiveStateDto active; 
    private final int points;

    public PlayerStateDto(String name, Set<Card> hand, ActiveStateDto active, int points) {
        this.name = name;
        this.hand = hand;
        this.active = active;
        this.points = points;
    }
    /*
    public PlayerStateDto(String name, Set<Card> hand, ActiveStateDto active) {
        this.name = name;
        this.hand = hand;
        this.active = active;
        this.points = 0;
    }
    */
    public PlayerStateDto(String name, Set<Card> hand, int points) {
        this.name = name;
        this.hand = hand;
        this.active = null;
        this.points = points;
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

    public int getPoints() {
        return points;
    }
    @Override
    public String toString() {
        return "PlayerStateDto [name=" + name + ", hand=" + hand + ", active=" + active + ", points=" + points + "]";
    }
}
