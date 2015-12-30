package com.valeriisosliuk.game.observer;

import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.CardDeckDto;
import com.valeriisosliuk.game.Game;
import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.service.MessageService;

@Component("cardHolderObserver")
public class CardHolderObserver implements Observer {
    
    @Autowired
    private MessageService messageService;

	@Override
	public void update(Observable observable, Object arg) {
	    if (!(arg instanceof CardHolder)) {
	        return;
	    }
	    Game game = (Game) observable;
	    CardHolder cardHolder = (CardHolder) arg; 
	    CardDeckDto dto = new CardDeckDto(cardHolder.getLastCardInDiscard(), cardHolder.cardDeckHasNext());
	    messageService.sendToAll(game.getPlayerNames(), dto);
	}

}
