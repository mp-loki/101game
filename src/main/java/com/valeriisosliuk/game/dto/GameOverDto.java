package com.valeriisosliuk.game.dto;

import java.util.List;

public class GameOverDto extends Dto {
    
    private final List<GameOverPlayerInfoDto> players;
    private final String winner;

    public GameOverDto(List<GameOverPlayerInfoDto> players, String winner) {
        super(DtoType.GAME_OVER);
        this.players = players;   
        this.winner = winner;
    }

    public List<GameOverPlayerInfoDto> getPlayers() {
        return players;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return "GameOverDto [winner=" + winner + ", players=" + players + "]";
    }

}
