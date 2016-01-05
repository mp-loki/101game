package com.valeriisosliuk.dto;

import java.util.Collections;
import java.util.List;

public class DealEndDto extends Dto {

    private final PlayerStateDto currentPlayer;
    private final List<PlayerStateDto> players;

    public DealEndDto(PlayerStateDto currentPlayer, List<PlayerStateDto> players) {
        super(DtoType.DEAL_END);
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    public List<PlayerStateDto> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public PlayerStateDto getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public String toString() {
        return "DealEndDto [currentPlayer=" + currentPlayer + ", players=" + players + "]";
    }
    
}
