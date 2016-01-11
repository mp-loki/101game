package com.valeriisosliuk.game.observer;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.ActivateDto;
import com.valeriisosliuk.game.dto.ActiveStateDto;
import com.valeriisosliuk.game.dto.DeactivateDto;
import com.valeriisosliuk.game.dto.HandInfoDto;
import com.valeriisosliuk.game.dto.HandUpdateDto;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.service.GameService;
import com.valeriisosliuk.game.service.MessageService;
import com.valeriisosliuk.game.service.ResponseService;
import com.valeriisosliuk.game.state.ActiveState;
import com.valeriisosliuk.game.state.PlayerStateChange;

@Component("playerObserver")
public class PlayerObserver implements Observer {
	
	private static final Logger log = Logger.getLogger(PlayerObserver.class);
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ResponseService responseService;
	
	@Override
	public void update(Observable o, Object arg) {
		Player player  = (Player) o;
		PlayerStateChange stateChange = (PlayerStateChange) arg;
		log.info("Player state changed! " + arg + " " + player);
		switch (stateChange) {
			case HAND_UPDATE :
				sendHandUpdateMessage(player);
				break;
			case ACTIVATE : 
			    sendActivateMessage(player);
			    break;
			case DEACTIVATE : 
			    sendDeactivateMessage(player);
			    break;
			default:;
		}
	}

	private void sendDeactivateMessage(Player player) {
	    List<String> playerNames = gameService.getGameInstance(player.getName()).get().getPlayerNames();
        messageService.sendToAll(playerNames, new DeactivateDto(player.getName()));
    }

    private void sendActivateMessage(Player player) {
	    ActiveState activeState = player.getActiveState();
	    ActiveStateDto activeStateDto = new ActiveStateDto(activeState.isPickAllowed(), activeState.isPassAllowed(), null);
	    ActivateDto activatePlayerDto = new ActivateDto(player.getName(), activeStateDto);
	    ActivateDto activateInfoDto = new ActivateDto(player.getName(), null);
	    messageService.send(player.getName(), activatePlayerDto);
	    List<String> otherPlayers = gameService.getGameMates(player.getName());
	    messageService.sendToAll(otherPlayers, activateInfoDto);
    }

    private void sendHandUpdateMessage(Player player) {
	    String playerName = player.getName();
	    ActiveState activeState = player.getActiveState();
	    ActiveStateDto activeStateDto = null;
	    if (activeState != null) {
	        activeStateDto = new ActiveStateDto(activeState.isPickAllowed(), activeState.isPassAllowed(), activeState.getTurnOptions()); 
	    }
		HandUpdateDto handUpdateDto = new HandUpdateDto(playerName, player.getHand(), activeStateDto);
		messageService.send(playerName, handUpdateDto);
		List<String> otherPlayers = gameService.getGameMates(playerName);
		HandInfoDto handInfoDto = new HandInfoDto(playerName, player.getHand().size());
		messageService.sendToAll(otherPlayers, handInfoDto);
	}

}
