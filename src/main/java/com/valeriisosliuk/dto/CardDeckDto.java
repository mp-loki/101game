package com.valeriisosliuk.dto;

import com.valeriisosliuk.model.Card;

public class CardDeckDto extends Dto {
    
    private final Card lastCard;
    private final boolean cardDeckHasNext;
    
    public CardDeckDto(Card lastCard, boolean cardDeckHasNext) {
        super(DtoType.CARD_DECK);
        this.lastCard = lastCard;
        this.cardDeckHasNext = cardDeckHasNext;
    }

    public Card getLastCard() {
        return lastCard;
    }

    public boolean isCardDeckHasNext() {
        return cardDeckHasNext;
    }
}
