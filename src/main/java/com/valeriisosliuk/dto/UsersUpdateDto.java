package com.valeriisosliuk.dto;

import java.util.Collections;
import java.util.List;

public class UsersUpdateDto {
    
    private final DtoType type = DtoType.USERS_UPDATE;
    private final List<OnlineUserDto> players;
    
    public UsersUpdateDto(List<OnlineUserDto> players) {
        this.players = players;
    }

    public DtoType getType() {
        return type;
    }

    public List<OnlineUserDto> getPlayers() {
        return Collections.unmodifiableList(players);
    }

	@Override
	public String toString() {
		return "UsersUpdateDto [type=" + type + ", players=" + players + "]";
	}
    
}
