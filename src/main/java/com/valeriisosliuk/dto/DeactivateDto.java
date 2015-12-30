package com.valeriisosliuk.dto;

public class DeactivateDto extends Dto {

    private final String name;

    public DeactivateDto(String name) {
        super(DtoType.DEACTIVATE);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "DeactivateDto [name=" + name + "]";
    }
}
