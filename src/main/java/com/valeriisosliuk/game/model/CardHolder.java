package com.valeriisosliuk.game.model;

import com.valeriisosliuk.game.observer.AbstractObservable;
import com.valeriisosliuk.game.observer.CardHolderObserver;
import com.valeriisosliuk.game.state.CardHolderStateChange;

public class CardHolder extends AbstractObservable {
	
	private CardDeck cardDeck;
	private Discard discard;
	
	public CardHolder() {
		CardHolderObserver observer = new CardHolderObserver();
		addObserver(observer);
	}
	
	public void setCardDeck(CardDeck cardDeck) {
		this.cardDeck = cardDeck; 
		discard = new Discard(cardDeck.getNext().get());
	}
	
	public Card getLastCardInDiscard() {
        return discard.getLastCard().orElseThrow(() -> new IllegalStateException("Discard must have at least one card in it"));
    }
	
	public Card pickCard() {
		if (!cardDeck.hasNext()) {
			turnOver();
			setChangedAndNotify(CardHolderStateChange.CARD_DECK_TURNED_OVER);
		}
		if (!cardDeck.hasNext()) {
			setChangedAndNotify(CardHolderStateChange.CARD_DECK_EMPTY);
		}
		return cardDeck.getNext().get();
	}

	private void turnOver() {
	        if (!cardDeck.hasNext()) {
	            cardDeck = discard.turnOver();
	        }
	}
	
	public void putCardInDiscard(Card card) {
    	discard.add(card);
    }

	public boolean cardDeckHasNext() {
		return cardDeck.hasNext();
	}

}
