package com.valeriisosliuk.game.state.actionhandler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.ActionType;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.state.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.game.Application.class, loader=AnnotationConfigContextLoader.class)
public class QuitHandlerTest {

    @Autowired
    private Game game;
    
    private QuitActionHandler quitHandler = new QuitActionHandler();
    
    @Before
    public void setUp() {
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
