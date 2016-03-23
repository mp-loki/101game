package com.valeriisosliuk.game.state.actionhandler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.ActionType;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.state.State;

public class QuitHandlerTest {

    private Game game;
    private QuitActionHandler quitHandler = new QuitActionHandler();
    
    @Before
    public void setUp() {
        game = new Game();
        game.joinGame("Kyle");
        game.joinGame("Stan");
        game.setState(State.TURN_START);
    }
    
    @Test
    public void testQuitAction() {
        Action action = new Action(ActionType.QUIT, "Kyle");
        assertEquals(State.GAME_OVER, quitHandler.handleAction(game, action));
    }
    
}
