package com.valeriisosliuk.model;

public class Player {

	private String name;
    private Hand hand;
	private int totalPoints;
	private boolean ready;
	private boolean pickAllowed;
	private boolean firstMove;
	private boolean endTurnAllowed;

	public Player(String name) {
	    this.name = name;
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
		return hand.getCards().size();
	}
	
}
