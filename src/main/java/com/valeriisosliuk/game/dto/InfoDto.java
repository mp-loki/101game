package com.valeriisosliuk.game.dto;

public class InfoDto extends Dto {

    private final String message;

    public InfoDto(String message) {
        super(DtoType.INFO);
        this.message = message;
    }

    @Override
    public String toString() {
        return "InfoDto [message=" + message + "]";
    }

    public String getMessage() {
        return message;
    }

}
