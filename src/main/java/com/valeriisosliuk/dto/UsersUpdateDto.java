package com.valeriisosliuk.dto;

import java.util.Collections;
import java.util.List;

public class UsersUpdateDto {
    
    private final DtoType type = DtoType.USERS_UPDATE;
    private final List<UserDto> users;
    
    public UsersUpdateDto(List<UserDto> users) {
        this.users = users;
    }

    public DtoType getType() {
        return type;
    }

    public List<UserDto> getUsers() {
        return Collections.unmodifiableList(users);
    }
}
