package com.valeriisosliuk.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Table;

@Component("startHandler")
public class StartHandler implements ActionHandler {

	@Autowired
	private TurnAdvisor  turnAdvisor;
	
	@Override
	public ActionResult handle(ActionDto action, Table table) {
		ActionResult result = new ActionResult();
		String playerName = action.getCurrentPlayer();
		boolean started = table.start(playerName);

		if (!started) {
			ResponseDto stateDto = getWaitDto(playerName);
			result.getPlayerUpdates().put(playerName, stateDto);
		} else {
			for (String player : table.getPlayers()) {
				result.getPlayerUpdates().put(player, getPlayerStateDto(player, table));
			}
			result.getGeneralUpdates().add(getGeneralMessageDto(table));
			table.getActivePlayer().setFirstMove(true);
		}
		return result;
	}

	private BroadcastDto getGeneralMessageDto(Table table) {
		BroadcastDto dto = new BroadcastDto();
		dto.getMessages().add("Game started");
		dto.getMessages().add(table.getActivePlayer().getName() + "'s turn");
		return dto;
	}

	private ResponseDto getWaitDto(String playerName) {
		ResponseDto dto = new ResponseDto();
		dto.setCurrentPlayerName(playerName);
		dto.getMessages().add("Waiting for other players");
		return dto;
	}

	public ResponseDto getPlayerStateDto(String playerName, Table table) {
		ResponseDto dto = new ResponseDto();
		dto.setCurrentPlayerName(playerName);
		dto.setLastCard(table.getLastCardInDiscard());
		dto.setHand(table.getPlayer(playerName).getHand().getCards());
		dto.setPlayerDetails(table.getSequencedPlayersList(playerName));
		boolean active = table.isActivePlayer(playerName);
		if (active) {
			dto.setValidTurnOptions(turnAdvisor.getValidCardsForTurn(table.getPlayersHandCards(playerName), 
					table.getLastCardInDiscard(), true));
		}
		dto.setPickAllowed(active);
		dto.setActive(active);
		return dto;
	}

}
