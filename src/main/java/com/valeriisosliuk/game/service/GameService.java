package com.valeriisosliuk.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.observer.GameObserver;

@Component
public class GameService {
	
	List<Game> games;
	
	@Autowired
	private GameObserver gameObserver;
	
	@PostConstruct
	public void init() {
		games = new ArrayList<>();
	}
	
	public Game joinGame(String username) {
		Game game = getGameInstance((g, m) -> g.getPlayerHolder().getPlayersCount() < m, Game.MAX_PLAYERS)
				.orElse(createNewGameInstance());
		game.joinGame(username);
		return game;
	}
	
	public Game getGame(String playerName) {
	    return getGameInstance(playerName).orElse(joinGame(playerName));
	}
	
	public Optional<Game> getGameInstance(String playerName) {
	    return getGameInstance((g ,n) -> g.isPlayerInGame(n), playerName);
	}
	
	private Game createNewGameInstance() {
		Game game = new Game();
		game.addObserver(gameObserver);
		games.add(game);
		return game;
	}
	
	public void dismissGame(Game game) {
		games.remove(game);
	}
	
	private <T> Optional<Game> getGameInstance(BiFunction<Game, T, Boolean> function, T t) {
		return games.stream().filter(game -> function.apply(game, t)).findFirst();
	}
	
}
