package com.valeriisosliuk.game.state.initializer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.DealStartDto;
import com.valeriisosliuk.dto.PlayerInfoDto;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.service.MessageService;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.util.Shuffle;

@Component("dealStartStateInitializer")
public class DealStartStateInitializer implements StateInitinalizer {

	@Autowired
	private MessageService messageService;

	private static final Logger log = Logger.getLogger(DealStartStateInitializer.class);

	@Override
	public void initializeState(Game game) {
		log.info("Starting new Deal");
		sendDealStartMessage(game);
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

	private void sendDealStartMessage(Game game) {
		for (Player player : game.getPlayers()) {
			DealStartDto dto = new DealStartDto(new PlayerInfoDto(player.getName(), 0, player.getTotalPoints()), game
					.getPlayerHolder().getSequencedPlayers(player).stream()
					.map(p -> new PlayerInfoDto(p.getName(), 0, p.getTotalPoints())).collect(Collectors.toList()));
			messageService.send(player.getName(), dto);
		}
	}
}
