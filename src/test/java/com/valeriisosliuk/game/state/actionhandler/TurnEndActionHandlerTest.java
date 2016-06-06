package com.valeriisosliuk.game.state.actionhandler;

import static org.junit.Assert.assertEquals;

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
import com.valeriisosliuk.game.model.Suit;
import com.valeriisosliuk.game.state.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.game.Application.class, loader=AnnotationConfigContextLoader.class)
public class TurnEndActionHandlerTest {
    
private TurnEndActionHandler actionHandler = new TurnEndActionHandler();
    
    @Autowired
    private Game game;
    
    @Before
    public void setUp() {
        game.joinGame("Kyle");
        game.joinGame("Stan");
        game.setState(State.TURN_START);
    }
    
    @Test
    public void testEndTurn() {
        game.getActivePlayer().getActiveState().setPassAllowed(true);
        Action action = new Action(ActionType.PASS, "Kyle");
        assertEquals(State.TURN_END, actionHandler.handleAction(game, action));
    }
    @Test
    public void testEndTurnNegative() {
        Action action = new Action(ActionType.PASS, "Kyle");
        assertEquals(State.TURN_START, actionHandler.handleAction(game, action));
    }
    
    @Test
    public void testRespondSuitTurnEnd() {
        game.setState(State.RESPOND_SUIT);
        game.getActivePlayer().getActiveState().setDemandedSuit(Suit.HEARTS);
        game.getActivePlayer().getActiveState().setPassAllowed(true);
        Action action = new Action(ActionType.PASS, "Kyle");
        assertEquals(State.RESPOND_SUIT,  actionHandler.handleAction(game, action));
        assertEquals("Stan", game.getActivePlayer().getName());
        assertEquals(Suit.HEARTS, game.getActivePlayer().getActiveState().getDemandedSuit());
    }

}
