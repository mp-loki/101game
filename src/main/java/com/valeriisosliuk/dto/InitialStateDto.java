package com.valeriisosliuk.dto;

import java.util.Collections;
import java.util.List;

import com.valeriisosliuk.game.state.State;

public class InitialStateDto extends Dto {

	private final List<OnlineUserDto> players;
	private final State state;
    
    public InitialStateDto(List<OnlineUserDto> players) {
        super(DtoType.INITIAL);
        this.state = State.INITIAL;
        this.players = players;
    }

    public List<OnlineUserDto> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public State getState() {
        return state;
    }
}
