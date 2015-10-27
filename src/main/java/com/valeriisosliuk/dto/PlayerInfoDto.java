package com.valeriisosliuk.dto;

public class PlayerInfoDto {

	private String name;
	private int cardCount;
	private int points;

	public PlayerInfoDto(String name, int cardCount, int points) {
		this.name = name;
		this.cardCount = cardCount;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCardCount() {
		return cardCount;
	}

	public void setCardCount(int cardCount) {
		this.cardCount = cardCount;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "PlayerInfoDto [name=" + name + ", cardCount=" + cardCount + ", points=" + points + "]";
	}
}
