package com.valeriisosliuk.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.CardDeckDto;
import com.valeriisosliuk.dto.GameStateDto;
import com.valeriisosliuk.dto.HandInfoDto;
import com.valeriisosliuk.dto.PlayerInfoDto;
import com.valeriisosliuk.dto.ActiveStateDto;
import com.valeriisosliuk.dto.PlayerStateDto;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.ActiveState;
import com.valeriisosliuk.game.state.State;

@Component("gameService")
public class GameService {

    List<Game> games;

    @Resource
    private Observer gameObserver;

    @Resource
    private Observer playerObserver;

    @Resource
    private Observer playerHolderObserver;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        games = new ArrayList<>();
    }

    public Game joinGame(String username) {
        Game game = getGameInstance((g, m) -> g.getState() == State.INITIAL && g.getPlayerHolder().getPlayersCount() < m, Game.MAX_PLAYERS).orElse(
                createNewGameInstance());
        Player player = game.joinGame(username);
        player.addObserver(playerObserver);
        return game;
    }

    public Game getGame(String playerName) {
        return getGameInstance(playerName).orElse(joinGame(playerName));
    }

    public Optional<Game> getGameInstance(String playerName) {
        return getGameInstance((g, n) -> g.isPlayerInGame(n), playerName);
    }

    private Game createNewGameInstance() {
        Game game = new Game();
        game.addObserver(gameObserver);
        game.getPlayerHolder().addObserver(playerHolderObserver);
        games.add(game);
        return game;
    }

    public void dismissGame(Game game) {
        games.remove(game);
        userService.setAvailable(game.getPlayers());
    }

    private <T> Optional<Game> getGameInstance(BiFunction<Game, T, Boolean> function, T t) {
        return games.stream().filter(game -> function.apply(game, t)).findFirst();
    }

    public List<String> getGameMates(String playerName) {
        return games.stream().filter(g -> g.getPlayerHolder().isPlayerInGame(playerName)).findFirst().get().getPlayerHolder().getSequencedPlayers(playerName).stream()
                .map(p -> p.getName()).collect(Collectors.toList());
    }
    
    public List<PlayerInfoDto> getMatesInfo(Player player, Game game) {
        return game.getPlayerHolder().getSequencedPlayers(player).stream()
                .map(p -> new PlayerInfoDto(p.getName(), p.getHand().size(), p.getTotalPoints())).collect(Collectors.toList());
    }

}
