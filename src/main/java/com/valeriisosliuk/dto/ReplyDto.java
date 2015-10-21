package com.valeriisosliuk.dto;

import java.util.List;
import java.util.Set;

import com.valeriisosliuk.model.Card;

public class ReplyDto {

	private Set<Card> hand;
	private String message;
	private List<String> players;

	public Set<Card> getHand() {
		return hand;
	}

	public void setHand(Set<Card> hand) {
		this.hand = hand;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}
}
