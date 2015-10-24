package com.valeriisosliuk.service.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;

@Component("passHandler")
public class PassHandler implements ActionHandler {

	@Override
	public ActionResult handle(ActionDto action, Table table) {
		ActionResult result = new ActionResult();
		Player currentPlayer = table.getActivePlayer();
		Player nextActivePlayer = table.getNextActivePlayer();
		Map<String, ResponseDto> playerUpdates = new HashMap<>();
		playerUpdates.put(currentPlayer.getName(), getPassStateDto(currentPlayer));
		playerUpdates.put(nextActivePlayer.getName(), getActivatedStateDto(nextActivePlayer));
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

	private ResponseDto getActivatedStateDto(Player nextActivePlayer) {
		ResponseDto dto = new ResponseDto();
		dto.setCurrentPlayerName(nextActivePlayer.getName());
		dto.setPickAllowed(true);
		dto.setActive(true);
		return dto;
	}

	private ResponseDto getPassStateDto(Player currentPlayer) {
		ResponseDto dto = new ResponseDto();
		dto.setCurrentPlayerName(currentPlayer.getName());
		dto.setPickAllowed(false);
		dto.setActive(false);
		return dto;
	}

}
