package com.valeriisosliuk.game.state.actionhandler;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.service.UserService;
import com.valeriisosliuk.game.state.State;

import static com.valeriisosliuk.game.state.State.*;

@Component("gameStartActionHandler")
public class GameStartActionHandler implements ActionHandler {

	@Autowired
	private UserService userService;
	
	@Override
	public State handleAction(Game game, Action action) {
		int joinedPlayersCount = game.getPlayers().size();
		if (game.getPlayers().size() == Game.MAX_PLAYERS || joinedPlayersCount >= Game.MIN_PLAYERS 
				&& CollectionUtils.isEmpty(userService.getAvailabePlayers())) {
			return DEAL_START;
			//return GAME_START; ???
		}
		return INITIAL;
	}
}
