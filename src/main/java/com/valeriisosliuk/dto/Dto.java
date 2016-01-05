package com.valeriisosliuk.dto;

public class Dto {
    
    private final DtoType type;

    public Dto(DtoType type) {
        super();
        this.type = type;
    }

    public DtoType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Dto [type=" + type + "]";
    }
}
