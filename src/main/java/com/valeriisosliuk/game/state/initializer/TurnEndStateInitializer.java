package com.valeriisosliuk.game.state.initializer;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;

import static com.valeriisosliuk.game.state.State.*;

import com.valeriisosliuk.model.Card;

import static com.valeriisosliuk.model.Rank.*;
import static com.valeriisosliuk.util.CardUtil.*;

@Component("turnEndStateInitializer")
public class TurnEndStateInitializer implements StateInitinalizer {

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

	private void processDefaultTurn(Game game) {
		Player activePlayer = game.getActivePlayer();
		setNextState(game, activePlayer.getHand());
	}

	private void processEightTurn(Game game, long eightCount) {
		Player activePlayer = game.getActivePlayer();
		Player skippedPlayer = game.getPlayerHolder().skipPlayer();
		pickCardAmount(skippedPlayer, game, eightCount * 2);
		setNextState(game, activePlayer.getHand());
	}

	private void processSevenTurn(Game game, long sevenCount) {
		Player activePlayer = game.getActivePlayer();
		Player nextPlayer = null;
		if (activePlayer.getHand().size() > 0) {
		    game.setState(TURN_START);
		    nextPlayer = game.getPlayerHolder().getNextActivePlayer();
		} else {
		    game.setState(DEAL_END);
		    nextPlayer = game.getPlayerHolder().skipPlayer();
		}
		pickCardAmount(nextPlayer, game, sevenCount);
	}

	private void processAceTurn(Game game) {
		Player activePlayer = game.getActivePlayer();
		game.getPlayerHolder().skipPlayer();
		setNextState(game, activePlayer.getHand());
	}
	
    private void setNextState(Game game, Set<Card> hand) {
        if (hand.size() > 0) {
            game.setState(TURN_START);
            game.getPlayerHolder().getNextActivePlayer();
        } else {
            game.setState(DEAL_END);
        }
    }
	
	private void processJackTurn(Game game) {
		State state = game.getActivePlayer().getHand().size() > 0 ? DEMAND_SUIT : DEAL_END;
		game.setState(state);
	}
	
	private void pickCardAmount(Player player, Game game, long amount) {
		for (int i = 0; i < amount; i++) {
			player.pickCard(game.getCardHolder().pickCard());
		}
	}
	
}
