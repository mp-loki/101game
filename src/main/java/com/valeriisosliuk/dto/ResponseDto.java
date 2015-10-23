package com.valeriisosliuk.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.valeriisosliuk.model.Card;

public class ResponseDto {

	private String currentPlayerName;
	private Card lastCard;
	private Set<Card> hand;
	private List<PlayerDetail> playerDetails;
	private List<String> messages;
	private boolean pickAllowed;
	private boolean active;

	public String getCurrentPlayerName() {
		return currentPlayerName;
	}

	public List<PlayerDetail> getPlayerDetails() {
		if (playerDetails == null) {
			playerDetails = new LinkedList<>();
		}
		return playerDetails;
	}

	public void setPlayerDetails(List<PlayerDetail> playerDetails) {
		this.playerDetails = playerDetails;
	}

	public void setCurrentPlayerName(String currentPlayerName) {
		this.currentPlayerName = currentPlayerName;
	}

	public Card getLastCard() {
		return lastCard;
	}

	public void setLastCard(Card lastCard) {
		this.lastCard = lastCard;
	}

	public Set<Card> getHand() {
		return hand;
	}

	public void setHand(Set<Card> hand) {
		this.hand = hand;
	}

	public List<String> getMessages() {
		if (messages == null) {
			messages = new LinkedList<>();
		}
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public boolean isPickAllowed() {
		return pickAllowed;
	}

	public void setPickAllowed(boolean pickAllowed) {
		this.pickAllowed = pickAllowed;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
