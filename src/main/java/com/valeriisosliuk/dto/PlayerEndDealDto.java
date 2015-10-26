package com.valeriisosliuk.dto;

import java.util.Set;

import com.valeriisosliuk.model.Card;

public class PlayerEndDealDto {

	private String name;
	private int totalPoints;
	private Set<Card> hand;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotlaPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int points) {
		this.totalPoints = points;
	}

	public Set<Card> getHand() {
		return hand;
	}

	public void setHand(Set<Card> hand) {
		this.hand = hand;
	}

	@Override
	public String toString() {
		return "[name=" + name + ", hand=" + hand + ", totalPoints=" + totalPoints + "]";
	}

}
