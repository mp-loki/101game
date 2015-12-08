package com.valeriisosliuk.dto;

import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;

public class Action {

    private ActionType type;
    private Card card;
    private String currentPlayer;
    
    public Action() {
	}
    
	public Action(ActionType type, Card card, String currentPlayer) {
		super();
		this.type = type;
		this.card = card;
		this.currentPlayer = currentPlayer;
	}

	public Action(ActionType type, String currentPlayer) {
		super();
		this.type = type;
		this.currentPlayer = currentPlayer;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public String getPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	@Override
	public String toString() {
		return "ActionDto [type=" + type + ", card=" + card + ", currentPlayer=" + currentPlayer + "]";
	}
	
}