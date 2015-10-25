package com.valeriisosliuk.service.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.dto.DtoFactory;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;

@Component("passHandler")
public class PassHandler implements ActionHandler {

	@Override
	public ActionResult handle(ActionDto action, Table table) {
		ActionResult result = new ActionResult();
		Player currentPlayer = table.getActivePlayer();
		currentPlayer.setPickAllowed(false);
		currentPlayer.setEndTurnAllowed(false);
		Player nextActivePlayer = table.getNextActivePlayer();
		Map<String, ResponseDto> playerUpdates = new HashMap<>();
		playerUpdates.put(currentPlayer.getName(), getStateDto(currentPlayer, table));
		playerUpdates.put(nextActivePlayer.getName(), getStateDto(nextActivePlayer, table));
		result.setPlayerUpdates(playerUpdates);
		result.getGeneralUpdates().add(getGeneralUpdate(currentPlayer, nextActivePlayer));
		return result;
	}

	private BroadcastDto getGeneralUpdate(Player currentPlayer, Player nextActivePlayer) {
		BroadcastDto dto = new BroadcastDto();
		dto.getMessages().add(currentPlayer.getName() + " Passes");
		dto.getMessages().add(nextActivePlayer.getName() + "'s turn");
		return dto;
	}

	private ResponseDto getStateDto(Player nextActivePlayer, Table table) {
		return DtoFactory.getResponseDto(nextActivePlayer, table);
	}
}
