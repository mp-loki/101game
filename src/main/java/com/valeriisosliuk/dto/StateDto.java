package com.valeriisosliuk.dto;


public abstract class StateDto {
    
    private ClientState state;

    public StateDto(ClientState state) {
        this.state = state;
    }
    
    public ClientState getState() {
        return state;
    }
}
