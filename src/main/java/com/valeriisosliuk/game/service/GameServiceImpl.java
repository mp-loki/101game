package com.valeriisosliuk.game.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.PlayerInfoDto;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;

@Component("gameService")
public class GameServiceImpl implements GameService {

    List<Game> games;
    
    @Value("${game.players.min}")
    private Integer MIN_PLAYERS;
    @Value("${game.players.max}")
    private Integer MAX_PLAYERS;

    @Autowired
    private UserService userService;
    
    @Autowired
    private GameProvider gameProvider;

    @PostConstruct
    public void init() {
        games = new ArrayList<>();
    }

    private Game joinGame(String username) {
        Game game = getGameInstance((g, m) -> g.getState() == State.INITIAL && g.getPlayerHolder().getPlayersCount() < m, MAX_PLAYERS).orElse(
                createNewGameInstance());
        game.joinGame(username);
        return game;
    }

    @Override
    public Game getGame(String playerName) {
        return getGameInstance(playerName).orElse(joinGame(playerName));
    }

    @Override
    public Optional<Game> getGameInstance(String playerName) {
        return getGameInstance((g, n) -> g.isPlayerInGame(n), playerName);
    }

    private Game createNewGameInstance() {
        Game game = gameProvider.getNewGame();
        games.add(game);
        return game;
    }

    @Override
    public void dismissGame(Game game) {
        games.remove(game);
        userService.setAvailable(game.getPlayers());
    }

    private <T> Optional<Game> getGameInstance(BiFunction<Game, T, Boolean> function, T t) {
        return games.stream().filter(game -> function.apply(game, t)).findFirst();
    }

    @Override
    public List<String> getGameMates(String playerName) {
        Optional<Game> gameOpt = games.stream().filter(g -> g.getPlayerHolder().isPlayerInGame(playerName)).findFirst();
        if (gameOpt.isPresent()) {
            return gameOpt.get().getPlayerHolder().getSequencedPlayers(playerName).stream().map(p -> p.getName()).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
        /*
        return games.stream().filter(g -> g.getPlayerHolder().isPlayerInGame(playerName)).findFirst().get().getPlayerHolder().getSequencedPlayers(playerName)
                .stream().map(p -> p.getName()).collect(Collectors.toList());
         */
    }

    @Override
    public List<PlayerInfoDto> getMatesInfo(Player player, Game game) {
        return game.getPlayerHolder().getSequencedPlayers(player).stream().map(p -> new PlayerInfoDto(p.getName(), p.getHand().size(), p.getTotalPoints()))
                .collect(Collectors.toList());
    }

}
