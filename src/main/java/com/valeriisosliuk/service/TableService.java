package com.valeriisosliuk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.DtoFactory;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Table;
import com.valeriisosliuk.service.handler.ActionHandler;
import com.valeriisosliuk.service.handler.ActionHandlerSupplier;
import com.valeriisosliuk.service.handler.NextTurnProcessor;

@Component
public class TableService {

	private List<Table> tables;

	@Autowired
	private ActionHandlerSupplier handlerSupplier;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private NextTurnProcessor nextTurnProcessor;

	@PostConstruct
	public void init() {
		tables = new ArrayList<>();
	}

	public Table getCurrentTableForplayer(String playerName) {
		return getTable((t, u) -> t.isPlayerAtTheTable(u), playerName).get();
	}

	public Table joinFirstAvailableTable(String playername) {
		return getTable((t, m) -> t.getPlayerNames().size() < m, Table.MAX_PLAYERS_AT_THE_TABLE).orElse(createNewTable());
	}

	public Table joinTable(String playerName) {
		Table table = getTable((t, u) -> t.isPlayerAtTheTable(u), playerName).orElse(
				joinFirstAvailableTable(playerName));
		table.joinTable(playerName);
		return table;
	}

	public void processAction(ActionDto action) {
		Table table = getCurrentTableForplayer(action.getCurrentPlayer());
		ActionResult result = null;
		ActionHandler actionHandler = handlerSupplier.getActionHandler(action.getType());
		if (actionHandler != null) {
			result = actionHandler.handle(action, table);
		}
		if (result != null) {
			messageService.processActionResult(result);
		}
	}
	
	private Table createNewTable() {
		Table table = new Table();
		tables.add(table);
		return table;
	}

	public <T> Optional<Table> getTable(BiFunction<Table, T, Boolean> function, T t) {
		return tables.stream().filter(table -> function.apply(table, t)).findFirst();
	}
	/**
	 * This method is to be called upon page refresh to provide state to user
	 * @param playerName player to get state for
	 * @return StateDto for specified player
	 */
	public ResponseDto getStateDto(String playerName) {
		Table table = joinTable(playerName);
		ResponseDto dto;
		if (table.isStarted()) {
			dto = DtoFactory.getResponseDto(table.getPlayer(playerName), table);
		} else {
			dto = new ResponseDto();
			dto.setCurrentPlayerName(playerName);
			dto.getMessages().add("Waiting for players");
		}
		return dto;
	}
}
