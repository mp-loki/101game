package com.valeriisosliuk.game.state;

import java.util.Observable;
import java.util.Set;

import com.valeriisosliuk.game.observer.ActivePlayerObserver;
import com.valeriisosliuk.model.Card;

public class ActiveState extends Observable {
	
	private boolean pickAllowed;
	private boolean passAllowed;
	private Set<Card> turnOptions;
	private Set<Card> currentTurnCards;
	private String name;
	
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

	public Set<Card> getCurrentTurnCards() {
		return currentTurnCards;
	}

	public void setCurrentTurnCards(Set<Card> currentTurnCards) {
		this.currentTurnCards = currentTurnCards;
	}
}
