package com.valeriisosliuk.dto;

public class GameOverPlayerInfoDto {

    private final String name;
    private final int points;
    
    public GameOverPlayerInfoDto(String name, int points) {
        super();
        this.name = name;
        this.points = points;
    }
    public String getName() {
        return name;
    }
    public int getPoints() {
        return points;
    }
}
