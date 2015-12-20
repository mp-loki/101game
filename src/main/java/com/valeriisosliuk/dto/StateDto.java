package com.valeriisosliuk.dto;


public abstract class StateDto {
    
    private final DtoType type;

    public StateDto(DtoType type) {
        this.type = type;
    }
    
    public DtoType getState() {
        return type;
    }
}
