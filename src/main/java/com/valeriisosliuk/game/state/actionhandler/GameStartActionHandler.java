package com.valeriisosliuk.game.state.actionhandler;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;
import static com.valeriisosliuk.game.state.State.*;

@Component("gameStartActionHandler")
public class GameStartActionHandler implements ActionHandler {

	@Override
	public State handleAction(Game game, Action action) {
		game.joinGame(action.getPlayerName());
		if (game.getPlayers().size() == Game.MAX_PLAYERS) {
			return DEAL_START;
		}
		return INITIAL;
	}
}
