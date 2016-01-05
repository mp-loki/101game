package com.valeriisosliuk.game.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Dto;
import com.valeriisosliuk.dto.CardDeckDto;
import com.valeriisosliuk.dto.HandInfoDto;
import com.valeriisosliuk.dto.InitialStateDto;
import com.valeriisosliuk.dto.GameStateDto;
import com.valeriisosliuk.dto.PlayerInfoDto;
import com.valeriisosliuk.dto.ActiveStateDto;
import com.valeriisosliuk.dto.PlayerStateDto;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.ActiveState;

@Component
public class ResponseService {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    public Dto getStateDto(String playerName) {
        Optional<Game> gameOpt = gameService.getGameInstance(playerName);
        if (gameOpt.equals(Optional.empty())) {
            return crateInitialStateDto();
        } else {
            Game game = gameOpt.get();
            /*
            if (game.getActivePlayer().getName().equals(playerName)) {
                return getActiveStateDto(game);
            } else {
                return getInactiveStateDto(game);
            }
            */
            /*
            switch (game.getState()) {
                case DEAL_START:
                case TURN_START:
                case TURN_IN_PROGRESS:
                case TURN_END: return getGameStateDto(playerName, game);
                case DEAL_END:
                    break;
                case DEMAND_SUIT:
                    break;
                case GAME_OVER:
                    break;
                case GAME_START:
                    break;
                case INITIAL:
                    break;
                case RESPOND_SUIT:
                    break;
                default:
                    break; 
                }
            return null;
            */
            return getGameStateDto(playerName, game);
        }
    }

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
