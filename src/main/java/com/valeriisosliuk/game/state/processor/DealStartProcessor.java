package com.valeriisosliuk.game.state.processor;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.model.Discard;
import com.valeriisosliuk.util.Shuffle;

@Component
public class DealStartProcessor extends AbstractStateProcessor {
	
	private static final Logger log = Logger.getLogger(DealStartProcessor.class);
	@Override
	public void initializeState(Game game) {
		log.info("Starting new Deal");
		List<Card> allCards = Arrays.asList(Card.values());		
		CardDeck cardDeck = new CardDeck(Shuffle.shuffle(allCards));
        
        for (Player player : game.getPlayers()) {
        	player.setHand(cardDeck.getInitialHand());
        }
        CardHolder cardHolder = new CardHolder();
		cardHolder.setCardDeck(cardDeck);
        game.setCardHolder(cardHolder);
        game.getActivePlayer();
        game.setState(State.TURN_START);
	}

	@Override
	public void applyAction(Game game, Action action) {

	}

	@Override
	protected boolean validateAction(Action action) {
		return false;
	}

}
