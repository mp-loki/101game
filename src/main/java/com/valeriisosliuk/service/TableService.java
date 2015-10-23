package com.valeriisosliuk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Table;
import com.valeriisosliuk.service.handler.ActionHandler;

@Component
public class TableService {

	private List<Table> tables;

	@Autowired
	@Qualifier("passHandler")
	private ActionHandler passHandler;
	
	@Autowired 
	@Qualifier("pickHandler")
	private ActionHandler pickHandler;
	
	@Autowired
	@Qualifier("startHandler")
	private ActionHandler startHandler;
	
	@Autowired
	private MessageService messageService;

	@PostConstruct
	public void init() {
		tables = new ArrayList<>();
	}

	public Table getCurrentTableForplayer(String playerName) {
		return getTable((t, u) -> t.isPlayerAtTheTable(u), playerName).get();
	}

	public Table joinFirstAvailableTable(String playername) {
		return getTable((t, m) -> t.getPlayers().size() < m, Table.MAX_PLAYERS_AT_THE_TABLE).orElse(createNewTable());
	}

	public Table joinTable(String playerName) {
		Table table = getTable((t, u) -> t.isPlayerAtTheTable(u), playerName).orElse(
				joinFirstAvailableTable(playerName));
		table.joinTable(playerName);
		return table;
	}

	public void processAction(ActionDto action) {
		ActionResult result = null;
		Table table = getCurrentTableForplayer(action.getCurrentPlayer());
		switch (action.getType()) {
		case PASS:  result = passHandler.handle(action, table);
					break;
		case PICK:  result = pickHandler.handle(action, table);
					break;
		case START: result = startHandler.handle(action, table);
					break;
		case ACTION: // handle ACTION
					break;
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
		ResponseDto dto = new ResponseDto();
		dto.setCurrentPlayerName(playerName);
		Table table = joinTable(playerName);

		if (table.isStarted()) {
			dto.setLastCard(table.getLastCardInDiscard());
			dto.setHand(table.getPlayer(playerName).getHand().getCards());
			dto.setPlayerDetails(table.getSequencedPlayersList(playerName));
			dto.setPickAllowed(table.isActivePlayer(playerName));
		} else {
			dto.getMessages().add("Waiting for players");
		}
		return dto;
	}
}
