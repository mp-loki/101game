package com.valeriisosliuk.dto;

import java.util.LinkedList;
import java.util.List;

import com.valeriisosliuk.model.Card;

public class BroadcastDto {
	
	private ResponseDtoType type = ResponseDtoType.BROADCAST;
	private Card lastCard;
	private PlayerCardsCountDto playerUpdate;
	private List<String> messages;
	public Card getLastCard() {
		return lastCard;
	}
	public void setLastCard(Card lastCard) {
		this.lastCard = lastCard;
	}
	public PlayerCardsCountDto getPlayerUpdate() {
		return playerUpdate;
	}
	public void setPlayerUpdate(PlayerCardsCountDto playerUpdate) {
		this.playerUpdate = playerUpdate;
	}
	public List<String> getMessages() {
		if (messages == null) {
			messages = new LinkedList<String>();
		}
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	@Override
	public String toString() {
		return "BroadcastDto [lastCard=" + lastCard + ", playerUpdate=" + playerUpdate + ", messages=" + messages + "]";
	}
	
	public ResponseDtoType getType() {
		return type;
	}
}
