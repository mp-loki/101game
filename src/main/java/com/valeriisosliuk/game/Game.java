package com.valeriisosliuk.game;

import static com.valeriisosliuk.game.state.State.INITIAL;
import static com.valeriisosliuk.game.state.State.TURN_START;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.model.PlayerHolder;
import com.valeriisosliuk.game.observer.AbstractObservable;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.initializer.InitialStateInitializer;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;
import com.valeriisosliuk.game.state.initializer.TurnStartInitializer;

public class Game extends AbstractObservable {

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
    public static final int MAX_POINTS = 100;
    
	private State gameState;
	private CardHolder cardHolder;
	
	private PlayerHolder playerHolder;
	private Map<State, StateInitinalizer> stateInitializers;
	
	@Autowired
	private InitialStateInitializer initialStateInitializer;
	
	@Autowired
	private TurnStartInitializer turnStartInitializer;

	public Game() {
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


	public State getState() {
		return gameState;
	}

	public void setState(State state) {
		this.gameState = state;
		setChangedAndNotify(this);
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
