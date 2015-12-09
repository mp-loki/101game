package com.valeriisosliuk.game.state.actionhandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.model.Suit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class RespondSuitPickHandlerTest  {
    
    private Game game;
    
    @Autowired
    private RespondSuitPickHandler respondSuitPickHandler;
    
    @Before
    public void setUp() {
        game = new Game();
        game.joinGame("Kyle");
        game.joinGame("Stan");
        game.setState(State.RESPOND_SUIT);
        game.getActivePlayer();
        List<Card> cards = new LinkedList<>(Arrays.asList(Card.JACK_OF_CLUBS, Card._10_OF_HEARTS, Card._10_OF_DIAMONDS));
        CardHolder cardHolder = new CardHolder();
        cardHolder.setCardDeck(new CardDeck(cards));
        game.setCardHolder(cardHolder);
        game.getActivePlayer().getActiveState().setDemandedSuit(Suit.HEARTS);
    }
    
    @Test
    public void testPickCard() {
        game.getActivePlayer().getActiveState().setDemandedSuit(Suit.HEARTS);
        Player player = game.getActivePlayer();
        player.setHand(EnumSet.of(Card.QUEEN_OF_SPADES));
        Action action = new Action(ActionType.PICK, "Kyle");
        State state = respondSuitPickHandler.handleAction(game, action);
        assertEquals(State.RESPOND_SUIT, state);
        assertFalse("Pick is allowed when it should not be", player.getActiveState().isPickAllowed());
        assertTrue("Pass is not allowed when it should be", player.getActiveState().isPassAllowed());
        assertEquals(EnumSet.of(Card._10_OF_HEARTS), player.getActiveState().getTurnOptions());
    }
    
    @Test
    public void testPickCardEndTurn() {
        game.getActivePlayer().getActiveState().setDemandedSuit(Suit.CLUBS);
        Player player = game.getActivePlayer();
        player.setHand(EnumSet.of(Card.QUEEN_OF_SPADES));
        Action action = new Action(ActionType.PICK, "Kyle");
        State state = respondSuitPickHandler.handleAction(game, action);
        assertEquals(State.RESPOND_SUIT_END, state);
        assertFalse("Pick is allowed when it should not be", player.getActiveState().isPickAllowed());
        assertTrue("Pass is not allowed when it should be", player.getActiveState().isPassAllowed());
        assertEquals(EnumSet.noneOf(Card.class), player.getActiveState().getTurnOptions());
    }

}
