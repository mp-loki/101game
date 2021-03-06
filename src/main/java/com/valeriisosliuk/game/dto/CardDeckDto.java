package com.valeriisosliuk.game.dto;

import com.valeriisosliuk.game.model.Card;

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

	@Override
	public String toString() {
		return "CardDeckDto [lastCard=" + lastCard + ", cardDeckHasNext=" + cardDeckHasNext + "]";
	}
    
}
