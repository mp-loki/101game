package com.valeriisosliuk.game.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.observer.UserHolderObserver;

@Component
public class UserService {
    
    @Autowired
    private UserHolder userHolder;
   
    @PostConstruct
    public void init() {
        userHolder.addObserver(new UserHolderObserver());
    }
    
    public String getCurrentUserName() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    public void addUser(String username) {
        userHolder.addUser(username);
    }

    public void removeUser(String username) {
        userHolder.removeUser(username);
    }

}
