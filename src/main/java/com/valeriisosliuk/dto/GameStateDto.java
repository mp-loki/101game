package com.valeriisosliuk.dto;

import java.util.List;

import com.valeriisosliuk.game.state.State;


public class GameStateDto extends Dto {
    
    private final PlayerStateDto currentPlayer;
    private final List<PlayerInfoDto> players;
    private final CardDeckDto cardDeck;
    private final State state;
    
    public GameStateDto(State state, PlayerStateDto currentPlayer, List<PlayerInfoDto> players, CardDeckDto cardDeck) {
        super(DtoType.STATE);
        this.currentPlayer = currentPlayer;
        this.players = players;
        this.cardDeck = cardDeck;
        this.state = state;
    }

    public PlayerStateDto getCurrentPlayer() {
        return currentPlayer;
    }

    public List<PlayerInfoDto> getPlayers() {
        return players;
    }

    public CardDeckDto getCardDeck() {
        return cardDeck;
    }

    public State getState() {
        return state;
    }

}
