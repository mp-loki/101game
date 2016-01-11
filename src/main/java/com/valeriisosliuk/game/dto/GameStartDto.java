package com.valeriisosliuk.game.dto;

import java.util.List;

public class GameStartDto {
	
	private final DtoType type;
	private final String currentPlayer;
	private final List<String> players;
	
	public GameStartDto(DtoType type, String currentPlayer, List<String> players) {
		super();
		this.type = type;
		this.currentPlayer = currentPlayer;
		this.players = players;
	}
	public DtoType getType() {
		return type;
	}
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	public List<String> getPlayers() {
		return players;
	}
}
