package com.valeriisosliuk.game.state.initializer;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.CardDeckDto;
import com.valeriisosliuk.dto.DealStartDto;
import com.valeriisosliuk.dto.PlayerInfoDto;
import com.valeriisosliuk.dto.PlayerStateDto;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.service.MessageService;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.model.CardDeck;
import com.valeriisosliuk.util.CardUtil;

@Component("dealStartStateInitializer")
public class DealStartStateInitializer implements StateInitinalizer {

    @Autowired
    private MessageService messageService;

    private static final Logger log = Logger.getLogger(DealStartStateInitializer.class);

    @Override
    public void initializeState(Game game) {
        log.info("Starting new Deal");
        CardDeck cardDeck = CardUtil.getShuffledCardDeck();
        CardHolder cardHolder = new CardHolder();
        cardHolder.setCardDeck(cardDeck);
        for (Player player : game.getPlayers()) {
            player.setHand(cardDeck.getInitialHand());
        }
        game.setCardHolder(cardHolder);
        sendDealStartMessage(game);
        game.setState(State.TURN_START);
    }

    private void sendDealStartMessage(Game game) {
        for (Player player : game.getPlayers()) {
            PlayerStateDto playerStateDto = new PlayerStateDto(player.getName(), player.getHand(), null);
            List<PlayerInfoDto> matesInfo = game.getPlayerHolder().getSequencedPlayers(player).stream()
                    .map(p -> new PlayerInfoDto(p.getName(), p.getHand().size(), p.getTotalPoints())).collect(Collectors.toList());
            CardDeckDto cardDeckDto = new CardDeckDto(game.getCardHolder().getLastCardInDiscard(), game.getCardHolder().cardDeckHasNext());
            DealStartDto dto = new DealStartDto(playerStateDto, matesInfo, cardDeckDto);
            messageService.send(player.getName(), dto);

        }
    }
}
