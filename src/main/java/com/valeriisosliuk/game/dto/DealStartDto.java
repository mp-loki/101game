package com.valeriisosliuk.game.dto;

import java.util.Collections;
import java.util.List;

public class DealStartDto {
	
	private final DtoType type;
	private final PlayerStateDto currentPlayer;
	private final List<PlayerInfoDto> players;
	private final CardDeckDto cardDeck;
	
	public DealStartDto(PlayerStateDto currentPlayer, List<PlayerInfoDto> players, CardDeckDto cardDeck) {
		this.type = DtoType.DEAL_START;
		this.currentPlayer = currentPlayer;
		this.players = players;
		this.cardDeck = cardDeck;
	}
	public DtoType getType() {
		return type;
	}
	public PlayerStateDto getCurrentPlayer() {
		return currentPlayer;
	}
	public List<PlayerInfoDto> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	
	public CardDeckDto getCardDeck() {
        return cardDeck;
    }

	@Override
    public String toString() {
        return "DealStartDto [type=" + type + ", currentPlayer=" + currentPlayer + ", players=" + players + ", cardDeck=" + cardDeck + "]";
    }
}
