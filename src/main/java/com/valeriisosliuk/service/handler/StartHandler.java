package com.valeriisosliuk.service.handler;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Table;
import com.valeriisosliuk.util.Shuffle;

@Component("startHandler")
public class StartHandler implements ActionHandler {

	@Autowired
	private TurnAdvisor turnAdvisor;
	
	@Autowired
	private DealProcessor dealProcessor;

	@Override
	public ActionResult handle(ActionDto action, Table table) {
		ActionResult result = null;
		String playerName = action.getCurrentPlayer();
		boolean started = table.start(playerName);

		if (!started) {
			ResponseDto stateDto = getWaitDto(playerName);
			result = new ActionResult();
			result.getPlayerUpdates().put(playerName, stateDto);
		} else {
		    result = dealProcessor.startDeal(table, Shuffle.shuffle(Arrays.asList(Card.values())));
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
}
