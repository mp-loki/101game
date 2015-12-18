package com.valeriisosliuk.dto;

import java.util.Collections;
import java.util.List;

public class PendingStartDto {
    
    private final List<PendingPlayerDto> players;
    private final DtoType type;
    
    public PendingStartDto(List<PendingPlayerDto> players) {
        type = DtoType.PENDING_START;
        this.players = players;
    }

    public List<PendingPlayerDto> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public DtoType getType() {
        return type;
    }
}
