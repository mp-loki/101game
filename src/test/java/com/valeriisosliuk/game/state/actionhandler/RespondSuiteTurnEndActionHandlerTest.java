package com.valeriisosliuk.game.state.actionhandler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.ActionType;

public class RespondSuiteTurnEndActionHandlerTest {
    
    private RespondSuiteTurnEndActionHandler actionHandler = new RespondSuiteTurnEndActionHandler();
    
    private Game game;
    
    @Before
    public void setUp() {
        game = new Game();
        game.joinGame("Kyle");
        game.joinGame("Stan");
        game.setState(State.RESPOND_SUIT);
    }
    
    @Test
    public void testEndTurn() {
        game.getActivePlayer().getActiveState().setPassAllowed(true);
        Action action = new Action(ActionType.END, "Kyle");
        assertEquals(State.RESPOND_SUIT_END, actionHandler.handleAction(game, action));
    }
    @Test
    public void testEndTurnNegative() {
        Action action = new Action(ActionType.END, "Kyle");
        assertEquals(State.RESPOND_SUIT, actionHandler.handleAction(game, action));
    }

}