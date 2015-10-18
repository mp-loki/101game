package com.valeriisosliuk.model;

public class GameState {

	private Hand hand;
	private int totalPoints;
	private boolean ready;

	public GameState() {
		totalPoints = 0;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
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

}
