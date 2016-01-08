package com.valeriisosliuk.model;

public enum Rank {
    
	ACE("Ace", 15),
	_6("6", 0), 
	_7("7", 0), 
	_8("8", 0), 
	_9("9", 0), 
	_10("10", 10), 
	JACK("Jack", 20), 
	QUEEN("Queen", 10),
	KING("King", 10);
	
	private String code;
	private int points;
	
	Rank(String code, int points) {
		this.code = code;
		this.points = points;
	}

	public String getCode() {
		return code;
	}

	public int getPoints() {
		return points;
	}
}
