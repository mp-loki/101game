package com.valeriisosliuk.service.handler;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.PlayerDetail;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.dto.DtoFactory;
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
			currentPlayer.setPickAllowed(false);
			result.getGeneralUpdates().add(getCardPickedDto(currentPlayer));
			result.getPlayerUpdates().put(currentPlayer.getName(), getHandUpdatedDto(currentPlayer, table));
			
		} else {
			ResponseDto dto = new ResponseDto();
			dto.getMessages().add("Cannot pick more cards");
			result.getPlayerUpdates().put(currentPlayer.getName(), dto);
		}
		return result;
	}
	
	private ResponseDto getHandUpdatedDto(Player currentPlayer, Table table) {
		ResponseDto dto = DtoFactory.getResponseDto(currentPlayer, table);
		if (table.getLastCardInDiscard().getRank() == Rank._6) {
			currentPlayer.setPickAllowed(true);
			dto.setPickAllowed(true);
		}
		return dto;
	}

	private BroadcastDto getCardPickedDto(Player currentPlayer) {
		BroadcastDto dto = new BroadcastDto();
		dto.getMessages().add(currentPlayer.getName() + " Picked a card");
		PlayerDetail playerDetail = new PlayerDetail(currentPlayer.getName(), currentPlayer.getHand().size());
		dto.setPlayerUpdate(playerDetail);
		return dto;
	}

	private Card getCardFromDeck(Table table) {
		if (!table.cardDeckHasNext()) {
			table.turnOver();
		}
		return table.getCardFromDeck().get();
	}

}
