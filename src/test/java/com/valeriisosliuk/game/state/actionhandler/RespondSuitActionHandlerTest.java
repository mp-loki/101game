package com.valeriisosliuk.game.state.actionhandler;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.model.Suit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class RespondSuitActionHandlerTest {
    
    private Game game;
    
    @Autowired
    private RespondSuitActionHandler respondSuitActionHandler;

    @Before
    public void setUp() {
        game = new Game();
        game.joinGame("Kyle");
        game.joinGame("Stan");
        game.setState(State.RESPOND_SUIT);
        game.getActivePlayer();
        List<Card> cards = new LinkedList<>(Arrays.asList(Card.JACK_OF_CLUBS, Card._10_OF_DIAMONDS));
        CardHolder cardHolder = new CardHolder();
        cardHolder.setCardDeck(new CardDeck(cards));
        game.setCardHolder(cardHolder);
    }
    
    @Test
    public void testRespondSuiteTurnEnd() {
        game.getActivePlayer().getActiveState().setDemandedSuit(Suit.HEARTS);
        game.getActivePlayer().setHand(EnumSet.of(Card.KING_OF_CLUBS));;
        Action action = new Action(ActionType.RESPOND, Card._8_OF_HEARTS, "Kyle");
        State state = respondSuitActionHandler.handleAction(game, action);
        assertEquals(State.TURN_END, state);
        assertEquals(Card._8_OF_HEARTS, game.getCardHolder().getLastCardInDiscard());
    }
    
    @Test
    public void testRespondSuiteTurnInProgress() {
        game.getActivePlayer().getActiveState().setDemandedSuit(Suit.HEARTS);
        game.getActivePlayer().setHand(EnumSet.of(Card._8_OF_CLUBS));;
        Action action = new Action(ActionType.RESPOND, Card._8_OF_HEARTS, "Kyle");
        State state = respondSuitActionHandler.handleAction(game, action);
        assertEquals(State.TURN_IN_PROGRESS, state);
        assertEquals(Card._8_OF_HEARTS, game.getCardHolder().getLastCardInDiscard());
    }
    
    @Test
    public void testRespondSuiteJack() {
        game.getActivePlayer().getActiveState().setDemandedSuit(Suit.HEARTS);
        game.getActivePlayer().setHand(EnumSet.of(Card._8_OF_CLUBS));;
        Action action = new Action(ActionType.RESPOND, Card.JACK_OF_CLUBS, "Kyle");
        State state = respondSuitActionHandler.handleAction(game, action);
        assertEquals(State.DEMAND_SUIT, state);
        assertEquals(Card.JACK_OF_CLUBS, game.getCardHolder().getLastCardInDiscard());
    }
    
    @Test
    public void testRespondSuiteNegative() {
        game.getActivePlayer().getActiveState().setDemandedSuit(Suit.HEARTS);
        game.getActivePlayer().setHand(EnumSet.of(Card.KING_OF_CLUBS));;
        Action action = new Action(ActionType.RESPOND, Card._8_OF_CLUBS, "Kyle");
        State state = respondSuitActionHandler.handleAction(game, action);
        assertEquals(State.RESPOND_SUIT, state);
    }


}
