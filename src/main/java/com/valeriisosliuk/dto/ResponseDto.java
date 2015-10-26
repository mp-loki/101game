package com.valeriisosliuk.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.valeriisosliuk.model.Card;

public class ResponseDto {

	private ResponseDtoType type = ResponseDtoType.RESPONSE;
	private String currentPlayerName;
	private Card lastCard;
	private Set<Card> hand;
	private Set<Card> validTurnOptions; 
	private List<PlayerCardsCountDto> playerDetails;
	private List<String> messages;
	private boolean pickAllowed;
	private boolean active;
	private boolean endTurnAllowed;

	public String getCurrentPlayerName() {
		return currentPlayerName;
	}

	public List<PlayerCardsCountDto> getPlayerDetails() {
		if (playerDetails == null) {
			playerDetails = new LinkedList<>();
		}
		return playerDetails;
	}

	public void setPlayerDetails(List<PlayerCardsCountDto> playerDetails) {
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

	public Set<Card> getValidTurnOptions() {
		return validTurnOptions;
	}

	public void setValidTurnOptions(Set<Card> validTurnOptions) {
		this.validTurnOptions = validTurnOptions;
	}

	public boolean isEndTurnAllowed() {
		return endTurnAllowed;
	}

	public void setEndTurnAllowed(boolean passAllowed) {
		this.endTurnAllowed = passAllowed;
	}
	
	public ResponseDtoType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "ResponseDto [currentPlayerName=" + currentPlayerName + ", lastCard=" + lastCard + ", hand=" + hand
				+ ", validTurnOptions=" + validTurnOptions + ", playerDetails=" + playerDetails + ", messages="
				+ messages + ", pickAllowed=" + pickAllowed + ", active=" + active + "]";
	}

}
