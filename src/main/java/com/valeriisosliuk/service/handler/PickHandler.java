package com.valeriisosliuk.service.handler;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Rank;
import com.valeriisosliuk.model.Table;

@Component("pickHandler")
public class PickHandler implements ActionHandler {

	@Override
	public ActionResult handle(ActionDto action, Table table) {
		Card lastCard = table.getLastCardInDiscard();
		Player currentPlayer = table.getActivePlayer();
		
		ActionResult result = new ActionResult();
		
		if (lastCard.getRank().equals(Rank._6) || table.getActivePlayer().isPickAllowed()) {
			currentPlayer.getHand().add(getCardFromDeck(table));
			result.getGeneralUpdates().add(getCardPickedDto(currentPlayer));
			result.getPlayerUpdates().put(currentPlayer.getName(), getHandUpdatedDto(currentPlayer, lastCard, table));
			
		} else {
			ResponseDto dto = new ResponseDto();
			dto.getMessages().add("Cannot pick more cards");
			result.getPlayerUpdates().put(currentPlayer.getName(), dto);
		}
		return result;
	}
	
	private ResponseDto getHandUpdatedDto(Player currentPlayer, Card lastCard, Table table) {
		ResponseDto dto = new ResponseDto();
		dto.setCurrentPlayerName(currentPlayer.getName());
		dto.setHand(currentPlayer.getHand().getCards());
		dto.setActive(table.isActivePlayer(currentPlayer));
		if (lastCard.getRank() != Rank._6) {
			currentPlayer.setPickAllowed(false);
			dto.setPickAllowed(false);
		}
		return dto;
	}

	private ResponseDto getCardPickedDto(Player currentPlayer) {
		ResponseDto dto = new ResponseDto();
		dto.getMessages().add(currentPlayer.getName() + " Picked a card");
		//PlayerDetail playerDetail = new PlayerDetail(currentPlayer.getName(), currentPlayer.getHand().getCards().size());
		//dto.getPlayerDetails().add(playerDetail);
		return dto;
	}

	private Card getCardFromDeck(Table table) {
		if (!table.cardDeckHasNext()) {
			table.turnOver();
		}
		return table.getCardFromDeck().get();
	}

}
