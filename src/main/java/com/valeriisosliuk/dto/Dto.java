package com.valeriisosliuk.dto;

public abstract class Dto {
    
    private final DtoType type;

    public Dto(DtoType type) {
        super();
        this.type = type;
    }

    public DtoType getType() {
        return type;
    }
    
}
