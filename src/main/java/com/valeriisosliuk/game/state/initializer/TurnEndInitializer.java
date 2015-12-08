package com.valeriisosliuk.game.state.initializer;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.model.PlayerHolder;
import com.valeriisosliuk.game.state.State;

import static com.valeriisosliuk.game.state.State.*;

import com.valeriisosliuk.model.Card;

import static com.valeriisosliuk.model.Rank.*;
import static com.valeriisosliuk.util.CardUtil.*;

@Component
public class TurnEndInitializer extends AbstractStateInitializer {

	@Override
	public void initializeState(Game game) {
		Set<Card> turnCards = game.getActivePlayer().getActiveState().getCurrentTurnCards();
		postProcessCards(turnCards, game);
	}

	private void postProcessCards(Set<Card> turnCards, Game game) {
		long sevenCount = 0;
		long eightCount = 0;
		
		if (countRank(turnCards, ACE) > 0) {
			processAceTurn(game);
		} else if ((sevenCount = countRank(turnCards, _7)) > 0) {
			processSevenTurn(game, sevenCount);
		} else if ((eightCount = countRank(turnCards, _8)) > 0) {
			processEightTurn(game, eightCount);
		} else if (countRank(turnCards, JACK) > 0) {
			processJackTurn(game);
		} else {
			processDefaultTurn(game);
		}
	}
	
	private void processJackTurn(Game game) {
		Player activePlayer = game.getActivePlayer();
		game.setState(getJackNextState(activePlayer.getHand()));
	}

	private void processDefaultTurn(Game game) {
		Player activePlayer = game.getActivePlayer();
		getDefaultNextPlayer(activePlayer.getHand(), game.getPlayerHolder());
		game.setState(getDefaultNextState(activePlayer.getHand()));
	}

	private void processEightTurn(Game game, long eightCount) {
		Player activePlayer = game.getActivePlayer();
		Player skippedPlayer = game.getPlayerHolder().skipPlayer();
		pickCardAmount(skippedPlayer, game, eightCount * 2);
		game.setState(getDefaultNextState(activePlayer.getHand()));
	}

	private void processSevenTurn(Game game, long sevenCount) {
		Player activePlayer = game.getActivePlayer();
		Player nextPlayer = getDefaultNextPlayer(activePlayer.getHand(), game.getPlayerHolder());
		pickCardAmount(nextPlayer, game, sevenCount);
		game.setState(getDefaultNextState(activePlayer.getHand()));
	}

	private void processAceTurn(Game game) {
		Player activePlayer = game.getActivePlayer();
		game.getPlayerHolder().skipPlayer();
		game.setState(getDefaultNextState(activePlayer.getHand()));
	}
	
	private Player getDefaultNextPlayer(Set<Card> hand, PlayerHolder playerHolder) {
		return hand.size() > 0 ? playerHolder.getNextActivePlayer() : playerHolder.skipPlayer();
	}
	
	private State getDefaultNextState(Set<Card> hand) {
		return hand.size() > 0 ? TURN_START : DEAL_END;
	}
	
	private State getJackNextState(Set<Card> hand) {
		return hand.size() > 0 ? DEMAND_SUITE : DEAL_END;
	}
	
	private void pickCardAmount(Player player, Game game, long amount) {
		for (int i = 0; i < amount; i++) {
			player.pickCard(game.getCardHolder().pickCard());
		}
	}
	
}
