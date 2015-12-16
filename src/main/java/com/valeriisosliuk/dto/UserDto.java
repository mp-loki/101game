package com.valeriisosliuk.dto;

public class UserDto {
    
    private String name;
    private Boolean available;
    
    public UserDto(String name, Boolean available) {
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
}
