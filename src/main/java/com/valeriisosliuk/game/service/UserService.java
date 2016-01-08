package com.valeriisosliuk.game.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.OnlineUserDto;
import com.valeriisosliuk.dto.PendingPlayerDto;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.observer.UserHolderObserver;

@Component
public class UserService {
    
    @Autowired
    private UserHolder userHolder;
    
    @Autowired
    private UserHolderObserver userHolderObserver;
    
    @PostConstruct
    public void init() {
        userHolder.addObserver(userHolderObserver);
    }
    
    public String getCurrentUserName() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
    
    public List<OnlineUserDto> getLoggedInUsers(){
        return userHolder.getLoggedInUsers();
    }
    
    public void setAvailable(Collection<Player> users) {
    	users.stream().map(p -> p.getName()).forEach(userHolder::setAvailable);
    }
    
    public void setAvailable(String user) {
        userHolder.setAvailable(user);
    }
    public void setBusy(Collection<Player> users) {
    	users.stream().map(p -> p.getName()).forEach(userHolder::setBusy);
    }
    
    public List<PendingPlayerDto> getPendingPlayers(Game game){
        return game.getPlayers().stream().map(p -> new PendingPlayerDto(p.getName())).collect(Collectors.toList());
    }

    public void addUser(String username) {
        userHolder.addUser(username);
    }

    public void removeUser(String username) {
        userHolder.removeUser(username);
    }

	public List<String> getAvailabePlayers() {
		return userHolder.getAvailableUsers();
	}

	public boolean isUserAvailable(String username) {
		return userHolder.isUserAvailable(username);
	}

}
