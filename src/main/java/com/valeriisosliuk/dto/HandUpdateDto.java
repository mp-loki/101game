package com.valeriisosliuk.dto;

import java.util.Collections;
import java.util.Set;

import com.valeriisosliuk.model.Card;

public class HandUpdateDto {
	
	private final DtoType type;
	private final String name;
	private final Set<Card> hand;
	
	public HandUpdateDto(String name, Set<Card> hand) {
		this.type = DtoType.HAND_UPDATE;
		this.name = name;
		this.hand = hand;
	}

	public DtoType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Set<Card> getHand() {
		return Collections.unmodifiableSet(hand);
	}
}
