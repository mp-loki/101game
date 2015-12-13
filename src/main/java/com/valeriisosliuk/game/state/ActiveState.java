package com.valeriisosliuk.game.state;

import java.util.Collections;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import com.valeriisosliuk.game.observer.ActivePlayerObserver;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Suit;

public class ActiveState extends Observable {
	
	private boolean pickAllowed;
	private boolean passAllowed;
	private Set<Card> turnOptions;
	private Set<Card> currentTurnCards;
	private String name;
	private Suit demandedSuit;
	
	public ActiveState(String name) {
		this.name = name;
		this.pickAllowed = true;
		this.passAllowed = false;
		ActivePlayerObserver observer = new ActivePlayerObserver();
		addObserver(observer);
	}

	public boolean isPickAllowed() {
		return pickAllowed;
	}

	public void setPickAllowed(boolean pickAllowed) {
		this.pickAllowed = pickAllowed;
	}

	public boolean isPassAllowed() {
		return passAllowed;
	}

	public void setPassAllowed(boolean passAllowed) {
		this.passAllowed = passAllowed;
	}

	public Set<Card> getTurnOptions() {
		return turnOptions;
	}

	public void setTurnOptions(Set<Card> turnOptions) {
		this.turnOptions = turnOptions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addCurrentTurnCard(Card card) {
		if (currentTurnCards == null) {
			currentTurnCards = new HashSet<Card>();
		}
		currentTurnCards.add(card);
	}

	public Set<Card> getCurrentTurnCards() {
		return currentTurnCards == null ? Collections.emptySet() : Collections.unmodifiableSet(currentTurnCards);
	}

	public void setCurrentTurnCards(Set<Card> currentTurnCards) {
		this.currentTurnCards = currentTurnCards;
	}

    public Suit getDemandedSuit() {
        return demandedSuit;
    }

    public void setDemandedSuit(Suit demandedSuit) {
        this.demandedSuit = demandedSuit;
    }
}
