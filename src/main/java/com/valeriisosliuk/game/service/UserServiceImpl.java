package com.valeriisosliuk.game.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.OnlineUserDto;
import com.valeriisosliuk.game.dto.PendingPlayerDto;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.observer.UserHolderObserver;

@Component
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserHolder userHolder;
    
    @Autowired
    private UserHolderObserver userHolderObserver;
    
    @PostConstruct
    public void init() {
        userHolder.addObserver(userHolderObserver);
    }
    @Override
    public String getCurrentUserName() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
    @Override
    public List<OnlineUserDto> getLoggedInUsers(){
        return userHolder.getLoggedInUsers();
    }
    @Override
    public void setAvailable(Collection<Player> users) {
    	users.stream().map(p -> p.getName()).forEach(userHolder::setAvailable);
    }
    @Override
    public void setAvailable(String user) {
        userHolder.setAvailable(user);
    }
    @Override
    public void setBusy(Collection<Player> users) {
    	users.stream().map(p -> p.getName()).forEach(userHolder::setBusy);
    }
    @Override
    public List<PendingPlayerDto> getPendingPlayers(Game game){
        return game.getPlayers().stream().map(p -> new PendingPlayerDto(p.getName())).collect(Collectors.toList());
    }
    @Override
    public void addUser(String username) {
        userHolder.addUser(username);
    }
    @Override
    public void removeUser(String username) {
        userHolder.removeUser(username);
    }
    @Override
	public List<String> getAvailabePlayers() {
		return userHolder.getAvailableUsers();
	}
    @Override
	public boolean isUserAvailable(String username) {
		return userHolder.isUserAvailable(username);
	}

}
