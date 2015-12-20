package com.valeriisosliuk.game;

import java.util.List;

import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.model.PlayerHolder;
import com.valeriisosliuk.game.observer.AbstractObservable;
import com.valeriisosliuk.game.state.State;

public class Game extends AbstractObservable {

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
    public static final int MAX_POINTS = 100;
    
	private State gameState;
	private CardHolder cardHolder;
	
	private PlayerHolder playerHolder;

	public Game() {
		setPlayerHolder(new PlayerHolder());
	}

	private void setPlayerHolder(PlayerHolder playerHolder) {
		this.playerHolder = playerHolder;
	}

	public Player joinGame(String playerName) {
		return getPlayerHolder().joinGame(playerName);
	}
	
	public boolean isPlayerInGame(String name) {
	    return playerHolder.isPlayerInGame(name);
	}

	public State getState() {
		return gameState;
	}

	public void setState(State state) {
		this.gameState = state;
		setChangedAndNotify(gameState);
	}

	public Player getActivePlayer() {
		return getPlayerHolder().getActivePlayer();
	}

	public List<Player> getPlayers() {
		return getPlayerHolder().getPlayers();
	}
	
	public List<Player> getSequencedPlayers(Player player) {
		return getPlayerHolder().getSequencedPlayers(player);
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
