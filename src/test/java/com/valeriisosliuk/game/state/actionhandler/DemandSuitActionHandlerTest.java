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
import com.valeriisosliuk.game.model.Suit;
import com.valeriisosliuk.game.state.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.game.Application.class, loader=AnnotationConfigContextLoader.class)
public class DemandSuitActionHandlerTest {
    
    @Autowired
    private Game game;
    
    @Autowired
    private DemandSuitActionHandler demandSuitActionHandler;

    @Before
    public void setUp() {
        game.joinGame("Kyle");
        game.joinGame("Stan");
        game.setState(State.DEMAND_SUIT);
        game.getActivePlayer();
    }

    @Test
    public  void testDemandSuit() {
        Action action = new Action();
        action.setType(ActionType.DEMAND);
        action.setDemandedSuit(Suit.HEARTS);
        State newState = demandSuitActionHandler.handleAction(game, action);
        assertEquals(State.RESPOND_SUIT, newState);
        assertEquals("Stan", game.getActivePlayer().getName());
        assertEquals(Suit.HEARTS, game.getActivePlayer().getActiveState().getDemandedSuit());
    }

}
