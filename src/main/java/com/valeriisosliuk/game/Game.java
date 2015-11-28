package com.valeriisosliuk.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.apache.log4j.Logger;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.state.GameState;
import com.valeriisosliuk.game.state.State;

import static com.valeriisosliuk.game.state.State.*;

import com.valeriisosliuk.game.state.processor.InitialStateProcessor;
import com.valeriisosliuk.game.state.processor.StateProcessor;
import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.model.PlayerHolder;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.model.Discard;

public class Game implements Observer {

	private static final Logger log = Logger.getLogger(Game.class);

	private GameState gameState;
	private CardHolder cardHolder;
	
	private PlayerHolder playerHolder;
	private Map<State, StateProcessor> stateProcessors;

	public Game() {
		gameState = new GameState(State.INITIAL);
		gameState.addObserver(this);
		playerHolder = new PlayerHolder();
		initStateProcessors();
	}

	private void initStateProcessors() {
		stateProcessors = new HashMap<State, StateProcessor>();
		stateProcessors.put(INITIAL, new InitialStateProcessor());

	}

	public void handleAction(Action action) {
		StateProcessor processor = stateProcessors.get(gameState);
		processor.applyAction(this, action);
	}

	public boolean joinGame(String playerName) {
		return playerHolder.joinGame(playerName);
	}

	@Override
	public void update(Observable o, Object arg) {
		log.info("State changed to: " + gameState.getState());
		StateProcessor processor = stateProcessors.get(gameState.getState());
		if (processor == null) {
			log.error("No StateProcessor found for state: " + gameState.getState());
			return;
		}
		processor.initializeState(this);
	}

	public State getState() {
		return gameState.getState();
	}

	public void setState(State state) {
		this.gameState.setState(state);
	}

	public Player getActivePlayer() {
		return playerHolder.getActivePlayer();
	}

	public List<Player> getPlayers() {
		return playerHolder.getPlayers();
	}

	public CardHolder getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(CardHolder cardHolder) {
		this.cardHolder = cardHolder;
	}

}
