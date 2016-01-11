package com.valeriisosliuk.game.state.initializer;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.DealEndDto;
import com.valeriisosliuk.game.dto.PlayerStateDto;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.service.MessageService;
import com.valeriisosliuk.game.state.State;

@Component("dealEndStateInitializer")
public class DealEndStateInitializer implements StateInitinalizer {

    @Autowired
    private MessageService messageService;
    
    @Override
    public void initializeState(Game game) {
        Player dealWinner = game.getActivePlayer();
        boolean gameOver = countPoints(game.getPlayerHolder().getPlayers());
        State state = null;
        if (gameOver) {
            state = State.GAME_OVER;
        } else {
            game.getPlayerHolder().resetIterator(dealWinner);
            sendBroadcastDealEnd(game);
            state = State.DEAL_START;
        }
        game.setState(state);
    }
    
    private void sendBroadcastDealEnd(Game game) {
        for (Player player : game.getPlayers()) {
            PlayerStateDto playerStateDto = new PlayerStateDto(player.getName(), player.getHand(), player.getTotalPoints());
            List<PlayerStateDto> otherPlayerStates = getOtherPlayersStates(game, player);
            DealEndDto dto = new DealEndDto(playerStateDto, otherPlayerStates);
            messageService.send(player.getName(), dto);
        }
    }
    private List<PlayerStateDto> getOtherPlayersStates(Game game, Player currentPlayer) {
        List<PlayerStateDto> otherPlayerStates = new LinkedList<PlayerStateDto>();
        for (Player player : game.getSequencedPlayers(currentPlayer)) {
            otherPlayerStates.add(new PlayerStateDto(player.getName(), player.getHand(), player.getTotalPoints()));
        }
        return otherPlayerStates;
    }

    private boolean countPoints(List<Player> players) {
        boolean gameOver = false;
        for (Player player : players) {
            player.addPoints(player.getHand().stream().mapToInt(c -> c.getRank().getPoints()).sum());
            if (player.getTotalPoints() > Game.MAX_POINTS) {
                gameOver = true;
            } 
        } 
        return gameOver;
    }
}
