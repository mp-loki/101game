package com.valeriisosliuk.dto;

public class HandInfoDto extends Dto {

    private final String name;
	private final int cardNum;

	public HandInfoDto(String name, int cardNum) {
	    super(DtoType.HAND_INFO);
		this.name = name;
		this.cardNum = cardNum;
	}

	public String getName() {
		return name;
	}

	public int getCardNum() {
		return cardNum;
	}

    @Override
    public String toString() {
        return "HandInfoDto [name=" + name + ", cardNum=" + cardNum + "]";
    }

}
