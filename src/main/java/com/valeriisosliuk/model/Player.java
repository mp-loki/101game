package com.valeriisosliuk.model;

import java.util.EnumSet;
import java.util.Set;

public class Player {

	private String name;
	private Set<Card> hand;
	private Set<Card> validNextMoveOptions;
	private int totalPoints;
	private boolean ready;
	private boolean pickAllowed;
	private boolean firstMove;
	private boolean endTurnAllowed;
	private Set<Card> currentTurnCards;

	public Player(String name) {
		this.name = name;
		totalPoints = 0;
	}

	public Set<Card> getHand() {
		return hand;
	}

	public void setHand(Set<Card> hand) {
		this.hand = hand;
	}

	public void addPoints(int points) {
		totalPoints += points;
	}

	public void subtractPoints(int points) {
		totalPoints -= points;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public boolean isPickAllowed() {
		return pickAllowed;
	}

	public void setPickAllowed(boolean pickAllowed) {
		this.pickAllowed = pickAllowed;
	}

	public String getName() {
		return name;
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	public boolean isEndTurnAllowed() {
		return endTurnAllowed;
	}

	public void setEndTurnAllowed(boolean isPassAllowed) {
		this.endTurnAllowed = isPassAllowed;
	}

	public int getHandSize() {
		return hand.size();
	}

	public Set<Card> getValidNextTurnOptions() {
		return validNextMoveOptions;
	}

	public void setValidNextMoveOptions(Set<Card> validNextTurnOptions) {
		this.validNextMoveOptions = validNextTurnOptions;
	}

	public Set<Card> getCurrentTurnCards() {
		if (currentTurnCards == null) {
			currentTurnCards = EnumSet.noneOf(Card.class);
		}
		return currentTurnCards;
	}
	public void setCurrentTurnCards(Set<Card> currentTurnCards) {
		this.currentTurnCards = currentTurnCards;
	}
}
