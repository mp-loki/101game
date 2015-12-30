package com.valeriisosliuk.dto;

import java.util.Collections;
import java.util.Set;

import com.valeriisosliuk.model.Card;

public class HandUpdateDto extends Dto {

    private final String name;
    private final Set<Card> hand;
    private final ActiveStateDto active;

    public HandUpdateDto(String name, Set<Card> hand, ActiveStateDto active) {
        super(DtoType.HAND_UPDATE);
        this.name = name;
        this.hand = hand;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public Set<Card> getHand() {
        return Collections.unmodifiableSet(hand);
    }

    public ActiveStateDto getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "HandUpdateDto [name=" + name + ", hand=" + hand + ", active=" + active + "]";
    }
}
