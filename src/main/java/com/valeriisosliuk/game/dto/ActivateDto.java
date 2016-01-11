package com.valeriisosliuk.game.dto;

public class ActivateDto extends Dto {
    
    private final String name;
    private final ActiveStateDto active;
    
    public ActivateDto(String name, ActiveStateDto active) {
        super(DtoType.ACTIVATE);
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public ActiveStateDto getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "ActivateDto [name=" + name + ", active=" + active + "]";
    }
}
