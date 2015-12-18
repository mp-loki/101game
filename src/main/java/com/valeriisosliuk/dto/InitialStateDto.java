package com.valeriisosliuk.dto;

import java.util.Collections;
import java.util.List;

public class InitialStateDto extends StateDto{

    private final List<OnlineUserDto> players;
    
    public InitialStateDto(List<OnlineUserDto> players) {
        super(ClientState.INITIAL);
        this.players = players;
    }

    public List<OnlineUserDto> getPlayers() {
        return Collections.unmodifiableList(players);
    }
    
}
