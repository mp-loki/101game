package com.valeriisosliuk.dto;

import java.util.Collections;
import java.util.List;

public class PendingStartDto {
    
	private final PendingPlayerDto currentPlayer;
    private final List<PendingPlayerDto> players;
    private final DtoType type;
    
    public PendingStartDto(PendingPlayerDto currentPlayer, List<PendingPlayerDto> players) {
        type = DtoType.PENDING_START;
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    public List<PendingPlayerDto> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public DtoType getType() {
        return type;
    }

	public PendingPlayerDto getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public String toString() {
		return "PendingStartDto [currentPlayer=" + currentPlayer + ", players=" + players + ", type=" + type + "]";
	}
}
