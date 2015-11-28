package com.valeriisosliuk.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.PlayerInfoDto;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.dto.DtoFactory;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Rank;
import com.valeriisosliuk.model.Table;

@Component("pickHandler")
public class PickHandler implements ActionHandler {
	
	@Autowired
	private NextTurnProcessor nextTurnProcessor;
	
	@Autowired
	private TurnAdvisor turnAdvisor;

	@Override
	public ActionResult handle(Action action, Table table) {
		Card lastCard = table.getLastCardInDiscard();
		Player currentPlayer = table.getActivePlayer();
		
		ActionResult result = new ActionResult();
		
		if (lastCard.getRank().equals(Rank._6) || table.getActivePlayer().isPickAllowed()) {
			currentPlayer.getHand().add(table.getCardFromDeck());
			currentPlayer.setPickAllowed(false);
			if (!lastCard.getRank().equals(Rank._6)) {
				currentPlayer.setEndTurnAllowed(true);
			}
			result.getGeneralUpdates().add(getCardPickedDto(currentPlayer));
			result.getPlayerUpdates().put(currentPlayer.getName(), getHandUpdatedDto(currentPlayer, table));
			checkTurnEnd(table);
		} else {
			ResponseDto dto = new ResponseDto();
			dto.getMessages().add("Cannot pick more cards");
			result.getPlayerUpdates().put(currentPlayer.getName(), dto);
		}
		currentPlayer.setValidNextMoveOptions(turnAdvisor.getValidCardsForTurn(currentPlayer.getHand(), table.getLastCardInDiscard(), 
				currentPlayer.isFirstMove()));
		nextTurnProcessor.processNextMove(table, result);
		return result;
	}
	
	private void checkTurnEnd(Table table) {
		// TODO Auto-generated method stub
		
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
		PlayerInfoDto playerDetail = new PlayerInfoDto(currentPlayer.getName(), currentPlayer.getHand().size(), currentPlayer.getTotalPoints());
		dto.setPlayerUpdate(playerDetail);
		return dto;
	}

	

}
