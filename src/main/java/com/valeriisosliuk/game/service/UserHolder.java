package com.valeriisosliuk.game.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.OnlineUserDto;
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
        loggedInUsers.put(username, true);
        setChangedAndNotify();
    }
    
    public void removeUser(String username) {
        loggedInUsers.remove(username);
        setChangedAndNotify();
    }
    
    public void setBusy(String username) {
        loggedInUsers.put(username, false);
        setChangedAndNotify();
    }
    
    public void setAvailable(String username) {
        loggedInUsers.put(username, true);
        setChangedAndNotify();
    }

    public List<OnlineUserDto> getLoggedInUsers() {
        return loggedInUsers.keySet().stream().map(u -> new OnlineUserDto(u, loggedInUsers.get(u))).collect(Collectors.toList());
    }
    public List<String> getAvailableUsers() {
    	return loggedInUsers.keySet().stream().filter(u -> loggedInUsers.get(u)).collect(Collectors.toList());
    }

	public boolean isUserAvailable(String username) {
		return loggedInUsers.get(username);
	}
}
