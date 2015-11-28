package com.valeriisosliuk.game.state.actionhandler;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.PlayerHolder;
import com.valeriisosliuk.game.state.State;
import static com.valeriisosliuk.game.state.State.*;

@Component
public class StartActionHandler implements ActionHandler {

	@Override
	public State handleAction(Game game, Action action) {
		game.joinGame(action.getPlayer());
		if (game.getPlayers().size() == PlayerHolder.MAX_PLAYERS) {
			return DEAL_START;
		}
		return INITIAL;
	}

}
