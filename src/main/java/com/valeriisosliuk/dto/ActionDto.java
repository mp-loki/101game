package com.valeriisosliuk.dto;

import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;

public class ActionDto {

    private ActionType type;
    private Card card;
    private String currentPlayer;
    
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

	public String getCurrentPlayer() {
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
