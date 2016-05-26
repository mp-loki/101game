package com.valeriisosliuk.game.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.ActiveStateDto;
import com.valeriisosliuk.game.dto.CardDeckDto;
import com.valeriisosliuk.game.dto.Dto;
import com.valeriisosliuk.game.dto.GameStateDto;
import com.valeriisosliuk.game.dto.InitialStateDto;
import com.valeriisosliuk.game.dto.PlayerInfoDto;
import com.valeriisosliuk.game.dto.PlayerStateDto;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.ActiveState;

@Component
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Override
    public Dto getStateDto(String playerName) {
        Optional<Game> gameOpt = gameService.getGameInstance(playerName);
        if (gameOpt.equals(Optional.empty())) {
            return crateInitialStateDto();
        } else {
            Game game = gameOpt.get();
            return getGameStateDto(playerName, game);
        }
    }
    @Override
    public GameStateDto getGameStateDto(String playerName, Game game) {
        Optional<Player> playerOpt = game.getPlayerHolder().getPlayer(playerName);
        if (playerOpt.equals(Optional.empty())) {
            throw new RuntimeException("Wrong Game instanc epicked for player: " + playerName);
        }
        Player player = playerOpt.get();
        ActiveStateDto active = null;
        if (game.isActive(player)) {
            ActiveState activeState = player.getActiveState();
            active = new ActiveStateDto(activeState.isPickAllowed(), activeState.isPassAllowed(), activeState.getTurnOptions());
        }
        PlayerStateDto userState = new PlayerStateDto(playerName, player.getHand(), active, player.getTotalPoints());
        List<PlayerInfoDto> matesInfo = gameService.getMatesInfo(player, game);
        CardDeckDto cardDeck = new CardDeckDto(game.getCardHolder().getLastCardInDiscard(), game.getCardHolder().cardDeckHasNext());
        return new GameStateDto(game.getState(), userState, matesInfo, cardDeck);
    }


    private InitialStateDto crateInitialStateDto() {
        InitialStateDto dto = new InitialStateDto(userService.getLoggedInUsers());
        return dto;
    }

}
