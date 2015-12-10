package com.valeriisosliuk.game;

import static com.valeriisosliuk.game.state.State.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.model.PlayerHolder;
import com.valeriisosliuk.game.state.GameState;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.initializer.InitialStateInitializer;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;
import com.valeriisosliuk.game.state.initializer.TurnStartInitializer;

public class Game implements Observer {

    public static final int MAX_POINTS = 100;
    
	private static final Logger log = Logger.getLogger(Game.class);

	private GameState gameState;
	private CardHolder cardHolder;
	
	private PlayerHolder playerHolder;
	private Map<State, StateInitinalizer> stateInitializers;
	
	@Autowired
	private InitialStateInitializer initialStateInitializer;
	
	@Autowired
	private TurnStartInitializer turnStartInitializer;

	public Game() {
		gameState = new GameState(State.INITIAL);
		gameState.addObserver(this);
		setPlayerHolder(new PlayerHolder());
		initStateProcessors();
	}

	private void setPlayerHolder(PlayerHolder playerHolder) {
		this.playerHolder = playerHolder;
	}

	private void initStateProcessors() {
		stateInitializers = new HashMap<State, StateInitinalizer>();
		stateInitializers.put(INITIAL, initialStateInitializer);
		stateInitializers.put(TURN_START, turnStartInitializer);
	}

	public boolean joinGame(String playerName) {
		return getPlayerHolder().joinGame(playerName);
	}

	@Override
	public void update(Observable o, Object arg) {
		log.info("State changed to: " + gameState.getState());
		StateInitinalizer processor = stateInitializers.get(gameState.getState());
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
		return getPlayerHolder().getActivePlayer();
	}

	public List<Player> getPlayers() {
		return getPlayerHolder().getPlayers();
	}

	public CardHolder getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(CardHolder cardHolder) {
		this.cardHolder = cardHolder;
	}

	public PlayerHolder getPlayerHolder() {
		return playerHolder;
	}
}
