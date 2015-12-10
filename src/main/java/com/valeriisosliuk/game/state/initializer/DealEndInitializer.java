package com.valeriisosliuk.game.state.initializer;

import java.util.List;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;

public class DealEndInitializer implements StateInitinalizer {

    @Override
    public void initializeState(Game game) {
        Player dealWinner = game.getActivePlayer();
        boolean gameOver = countPoints(game.getPlayerHolder().getPlayers());
        if (gameOver) {
            game.setState(State.GAME_OVER);
        } else {
            // TODO broadcast message with all cards and total points
            game.getPlayerHolder().resetIterator(dealWinner);
            game.setState(State.DEAL_START);
        }
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
