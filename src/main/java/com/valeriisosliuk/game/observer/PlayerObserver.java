package com.valeriisosliuk.game.observer;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.HandUpdateDto;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.service.MessageService;
import com.valeriisosliuk.game.state.PlayerStateChange;

@Component("playerObserver")
public class PlayerObserver implements Observer {
	
	private static final Logger log = Logger.getLogger(PlayerObserver.class);
	
	@Autowired
	private MessageService messageService;
	
	@Override
	public void update(Observable o, Object arg) {
		Player player  = (Player) o;
		PlayerStateChange stateChange = (PlayerStateChange) arg;
		log.info("Player state changed! " + arg + " " + player);
		switch (stateChange) {
			case HAND_UPDATE :
				sendHandUpdateMessage(player);
				break;
			default:;
		}
	}

	private void sendHandUpdateMessage(Player player) {
		HandUpdateDto handUpdateDto = new HandUpdateDto(player.getName(), player.getHand());
		messageService.send(player.getName(), handUpdateDto);
	}

}
