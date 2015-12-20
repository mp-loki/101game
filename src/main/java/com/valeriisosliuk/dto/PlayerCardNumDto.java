package com.valeriisosliuk.dto;

public class PlayerCardNumDto {

	private final String name;
	private final int cardNum;

	public PlayerCardNumDto(String name, int cardNum) {
		super();
		this.name = name;
		this.cardNum = cardNum;
	}

	public String getName() {
		return name;
	}

	public int getCardNum() {
		return cardNum;
	}
}
