package com.valeriisosliuk.game.model;

import java.util.List;
import java.util.stream.Collectors;

import com.valeriisosliuk.game.observer.AbstractObservable;
import com.valeriisosliuk.game.state.State;

public class Game extends AbstractObservable {
    
    @Deprecated
    public static final int MIN_PLAYERS = 2;
    @Deprecated
    public static final int MAX_PLAYERS = 4;
    @Deprecated
    public static final int MAX_POINTS = 100;
    
	private State gameState;
	private CardHolder cardHolder;
	
	private PlayerHolder playerHolder;
	
	public void setPlayerHolder(PlayerHolder playerHolder) {
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
	
    public List<String> getPlayerNames() {
        return getPlayerHolder().getPlayers().stream().map(Player::getName).collect(Collectors.toList());
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

    public boolean isActive(Player player) {
        return playerHolder.isActive(player);
    }

    public void putCardInDiscard(Card card) {
        cardHolder.putCardInDiscard(card);
        setChangedAndNotify(cardHolder);
    }

    public String getWinner() {
        return playerHolder.getWinner();
    }
}
