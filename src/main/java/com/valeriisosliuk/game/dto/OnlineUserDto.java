package com.valeriisosliuk.game.dto;

public class OnlineUserDto {
    
    private String name;
    private Boolean available;
    
    public OnlineUserDto(String name, Boolean available) {
        this.name = name;
        this.available = available;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getAvailable() {
        return available;
    }
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "UserDto [name=" + name + ", available=" + available + "]";
    }
}
