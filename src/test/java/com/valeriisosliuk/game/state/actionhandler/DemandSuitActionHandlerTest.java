package com.valeriisosliuk.game.state.actionhandler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.ActionType;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Suit;
import com.valeriisosliuk.game.state.State;

public class DemandSuitActionHandlerTest {

    private Game game;
    private DemandSuitActionHandler demandSuitActionHandler;

    @Before
    public void setUp() {
        game = new Game();
        game.joinGame("Kyle");
        game.joinGame("Stan");
        game.setState(State.DEMAND_SUIT);
        game.getActivePlayer();
        demandSuitActionHandler = new DemandSuitActionHandler();
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
