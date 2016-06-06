package com.valeriisosliuk.game.state;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.valeriisosliuk.game.model.Card;
import com.valeriisosliuk.game.model.Suit;
import com.valeriisosliuk.game.observer.AbstractObservable;

public class ActiveState extends AbstractObservable {
	
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
	}

	public boolean isPickAllowed() {
		return pickAllowed;
	}
	
	public void update(boolean pickAllowed, boolean passAllowed, Set<Card> turnOptions) {
	    this.pickAllowed = pickAllowed;
	    this.passAllowed = passAllowed;
	    this.turnOptions = turnOptions;
	    setChangedAndNotify();
	}
	
	@Deprecated
	public void setPickAllowed(boolean pickAllowed) {
		this.pickAllowed = pickAllowed;
	}

	public boolean isPassAllowed() {
		return passAllowed;
	}
	@Deprecated
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

	@Override
	public String toString() {
		return "ActiveState [name=" + name + ", pickAllowed=" + pickAllowed + ", passAllowed=" + passAllowed
				+ ", turnOptions=" + turnOptions + ", currentTurnCards=" + currentTurnCards + ", demandedSuit="
				+ demandedSuit + "]";
	}
    
}
