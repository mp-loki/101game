package com.valeriisosliuk.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;

@Component("passHandler")
public class PassHandler implements ActionHandler {
	
	@Autowired
	private NextTurnProcessor nextTurnProcessor;

	@Override
	public ActionResult handle(Action action, Table table) {
		ActionResult result = new ActionResult();
		Player currentPlayer = table.getActivePlayer();
		currentPlayer.setPickAllowed(false);
		currentPlayer.setEndTurnAllowed(false);
		result.getGeneralUpdates().add(getGeneralUpdate(currentPlayer));
		nextTurnProcessor.processCurrentTurnCards(table, currentPlayer.getCurrentTurnCards(), result);
		return result;
	}
	
	private BroadcastDto getGeneralUpdate(Player currentPlayer) {
		BroadcastDto dto = new BroadcastDto();
		dto.getMessages().add(currentPlayer.getName() + " Passes");
		return dto;
	}
}
