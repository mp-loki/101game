package com.valeriisosliuk.dto;

public class PlayerCardsCountDto {

	private String name;
	private int cardCount;

	public PlayerCardsCountDto(String name, int cardCount) {
		this.name = name;
		this.cardCount = cardCount;
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
}
