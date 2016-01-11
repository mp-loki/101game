package com.valeriisosliuk.game.dto;

public class PlayerInfoDto {

	private String name;
	private int cardNum;
	private int points;

	public PlayerInfoDto(String name, int cardNum, int points) {
		this.name = name;
		this.cardNum = cardNum;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "PlayerInfoDto [name=" + name + ", cardNum=" + cardNum + ", points=" + points + "]";
	}
}
