package com.valeriisosliuk.game.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.observer.AbstractObservable;

@Component
public class UserHolder extends AbstractObservable {
    
    private Map<String, Boolean> loggedInUsers;
    
    @PostConstruct
    public void init() {
        loggedInUsers = new HashMap<>();
    }
       
    public void addUser(String username) {
        if (loggedInUsers.keySet().contains(username)) {
            return;
        }
        loggedInUsers.put(username, false);
        setChangedAndNotify();
    }
    
    public void removeUser(String username) {
        loggedInUsers.remove(username);
        setChangedAndNotify();
    }
    
    public void setBusy(String username) {
        loggedInUsers.put(username, true);
        setChangedAndNotify();
    }
    
    public void setAvailable(String username) {
        loggedInUsers.put(username, false);
        setChangedAndNotify();
    }

    public Map<String, Boolean> getLoggedInUsers() {
        return Collections.unmodifiableMap(loggedInUsers);
    }
}
