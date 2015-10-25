package com.valeriisosliuk.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.DtoFactory;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;

@Component("startHandler")
public class StartHandler implements ActionHandler {

	@Autowired
	private TurnAdvisor turnAdvisor;

	@Override
	public ActionResult handle(ActionDto action, Table table) {
		ActionResult result = new ActionResult();
		String playerName = action.getCurrentPlayer();
		boolean started = table.start(playerName);

		if (!started) {
			ResponseDto stateDto = getWaitDto(playerName);
			result.getPlayerUpdates().put(playerName, stateDto);
		} else {
			for (Player player : table.getPlayers()) {
				if (table.isActivePlayer(player)) {
					player.setValidNextMoveOptions(turnAdvisor.getValidCardsForTurn(player.getHand(), table.getLastCardInDiscard(), true));
				}
				result.getPlayerUpdates().put(player.getName(), getPlayerStateDto(player, table));
			}
			result.getGeneralUpdates().add(getGeneralMessageDto(table));
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

	public ResponseDto getPlayerStateDto(Player player, Table table) {
		ResponseDto dto = DtoFactory.getResponseDto(player, table);
		dto.setPlayerDetails(table.getSequencedPlayersList(player.getName()));
		return dto;
	}

}
