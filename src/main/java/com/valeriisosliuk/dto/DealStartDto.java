package com.valeriisosliuk.dto;

import java.util.Collections;
import java.util.List;

public class DealStartDto {
	
	private final DtoType type;
	private final PlayerInfoDto currentPlayer;
	private final List<PlayerInfoDto> players;
	
	public DealStartDto(PlayerInfoDto currentPlayer, List<PlayerInfoDto> players) {
		this.type = DtoType.DEAL_START;
		this.currentPlayer = currentPlayer;
		this.players = players;
	}
	public DtoType getType() {
		return type;
	}
	public PlayerInfoDto getCurrentPlayer() {
		return currentPlayer;
	}
	public List<PlayerInfoDto> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	
	@Override
	public String toString() {
		return "DealStartDto [type=" + type + ", currentPlayer=" + currentPlayer + ", players=" + players + "]";
	}
	
}
