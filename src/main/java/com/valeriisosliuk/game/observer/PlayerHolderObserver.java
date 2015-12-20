package com.valeriisosliuk.game.observer;

import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.model.PlayerHolder;
import com.valeriisosliuk.game.service.UserService;

@Component("playerHolderObserver")
public class PlayerHolderObserver implements Observer {
	
	@Autowired
	private UserService userService;
	
	@Override
	public void update(Observable o, Object arg) {
		PlayerHolder playerHolder = (PlayerHolder) o;
		userService.setBusy(playerHolder.getPlayers());
	}

}
