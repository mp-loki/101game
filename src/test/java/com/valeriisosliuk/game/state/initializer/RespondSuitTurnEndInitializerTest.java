package com.valeriisosliuk.game.state.initializer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.Suit;

public class RespondSuitTurnEndInitializerTest {
    

    private RespondSuitTurnEndInitializer respondSuitTurnEndInitializer = new RespondSuitTurnEndInitializer();
    
    private Game game;
    
    @Before
    public void setUp() {
        game = new Game();
        game.joinGame("Kyle");
        game.joinGame("Stan");
        game.joinGame("Cartman");
        game.setState(State.RESPOND_SUIT_END);
        game.getActivePlayer().getActiveState().setDemandedSuit(Suit.HEARTS);
    }
    
    @Test
    public void testRespondSuitTurnEnd() {
        respondSuitTurnEndInitializer.initializeState(game);
        assertEquals(State.RESPOND_SUIT, game.getState());
        assertEquals("Stan", game.getActivePlayer().getName());
        assertEquals(Suit.HEARTS, game.getActivePlayer().getActiveState().getDemandedSuit());
    }

}
