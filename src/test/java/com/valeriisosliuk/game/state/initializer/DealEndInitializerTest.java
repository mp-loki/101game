package com.valeriisosliuk.game.state.initializer;

import static org.junit.Assert.*;

import java.util.EnumSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.Card;

public class DealEndInitializerTest {

    private DealEndInitializer dealEndInitializer = new DealEndInitializer();
    private Game game;
    
    @Before
    public void setUp() {
        game = new Game();
        game.joinGame("Kyle");
        game.joinGame("Stan");
        game.joinGame("Cartman");
        game.setState(State.DEAL_END);
        List<Player> players = game.getPlayerHolder().getPlayers();
        players.get(0).setHand(EnumSet.noneOf(Card.class));
        players.get(1).setHand(EnumSet.of(Card._6_OF_CLUBS, Card._8_OF_DIAMONDS, Card._10_OF_CLUBS));
        players.get(2).setHand(EnumSet.of(Card.ACE_OF_CLUBS, Card.JACK_OF_SPADES));
    }
    
    @Test
    public void testEndDealZeroCounts() {
        dealEndInitializer.initializeState(game);
        assertEquals(State.DEAL_START, game.getState());
        List<Player> players = game.getPlayerHolder().getPlayers();
        assertEquals(0, players.get(0).getTotalPoints());
        assertEquals(10, players.get(1).getTotalPoints());
        assertEquals(35, players.get(2).getTotalPoints());
    }
    
    @Test
    public void testEndDealNonZeroCounts() {
        List<Player> players = game.getPlayerHolder().getPlayers();
        players.get(0).addPoints(50);
        players.get(1).addPoints(50);
        players.get(2).addPoints(50);
        dealEndInitializer.initializeState(game);
        assertEquals(State.DEAL_START, game.getState());
        assertEquals(50, players.get(0).getTotalPoints());
        assertEquals(60, players.get(1).getTotalPoints());
        assertEquals(85, players.get(2).getTotalPoints());
    }
    
    @Test
    public void testGameOver() {
        List<Player> players = game.getPlayerHolder().getPlayers();
        players.get(0).addPoints(50);
        players.get(1).addPoints(50);
        players.get(2).addPoints(90);
        dealEndInitializer.initializeState(game);
        assertEquals(State.GAME_OVER, game.getState());
        assertEquals(50, players.get(0).getTotalPoints());
        assertEquals(60, players.get(1).getTotalPoints());
        assertEquals(125, players.get(2).getTotalPoints());
    }
    
    
}
