package com.valeriisosliuk.game.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.valeriisosliuk.game.observer.AbstractObservable;
import com.valeriisosliuk.game.state.ActiveState;
import com.valeriisosliuk.game.state.PlayerStateChange;
import com.valeriisosliuk.model.Card;

public class Player extends AbstractObservable {
	private String name;
	private Set<Card> hand;
	private int totalPoints;
	private ActiveState activeState;

	public Player(String name) {
		this.name = name;
		hand = new HashSet<Card>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Card> getHand() {
		return Collections.unmodifiableSet(hand);
	}

	public boolean removeCard(Card card) {
		return hand.remove(card);
	}

	public void pickCard(Card card) {
		hand.add(card);
		setChangedAndNotify(PlayerStateChange.HAND_UPDATE);
	}

	public void setHand(Set<Card> hand) {
		this.hand = hand;
		setChangedAndNotify(PlayerStateChange.HAND_UPDATE);
	}

	public ActiveState getActiveState() {
		return activeState;
	}

	void activate() {
		activeState = new ActiveState(name);
		setChangedAndNotify(PlayerStateChange.ACTIVATE);
	}

	void deactivate() {
		if (activeState != null) {
			activeState.deleteObservers();
			activeState = null;
		}
		setChangedAndNotify(PlayerStateChange.DEACTIVATE);
	}

	void skip() {
		setChangedAndNotify(PlayerStateChange.SKIP);
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public void addPoints(int points) {
		this.totalPoints += points;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", totalPoints=" + totalPoints + ", hand=" + hand + "]";
	}
}
