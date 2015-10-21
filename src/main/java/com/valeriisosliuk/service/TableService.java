package com.valeriisosliuk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.InfoDto;
import com.valeriisosliuk.dto.ReplyDto;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;

@Component
public class TableService {

	public List<Table> tables;
	
	private int id = 0;

	@Autowired 
	private MessageService messageService;

	@PostConstruct
	public void init() {
		tables = new ArrayList<>();
	}
	
	public Table getCurrentTableForUser(String userName) {
		return getTable((t, u) -> t.isPlayerAtTheTable(u), userName).get();
	}
	
	public Table joinFirstAvailableTable(String username) {
		return getTable((t, m) -> t.getPlayers().size() < m, Table.MAX_PLAYERS_AT_THE_TABLE)
				.orElse(createNewTable(id));
	}

	public Table joinTable(String userName, Integer id) {
		Table table = getTable((t, u) -> t.isPlayerAtTheTable(u), userName).orElse(
				getTable((t, i) -> t.getId() == i, id).orElse(createNewTable(id)));
		table.joinTable(userName);
		return table;
	}

	private Table createNewTable(Integer id) {
		Table table = new Table(id);
		tables.add(table);
		return table;
	}

	public <T> Optional<Table> getTable(BiFunction<Table, T, Boolean> function, T t) {
		return tables.stream().filter(table -> function.apply(table, t)).findFirst();
	}

    public void tryStart(String currentPlayerName) {
        Table table = getCurrentTableForUser(currentPlayerName);
        boolean started = table.start(currentPlayerName);
        
        if (!started) {
            ReplyDto replyDto = getWaitDto();
            messageService.send(currentPlayerName, replyDto);
        } else {
            for (String player : table.getPlayers()) {
                messageService.send(player, getStartGameReplyDto(table, player));
            }
            messageService.sendToAll(getStartGameInfoDto(table));
        }
    }

    private InfoDto getStartGameInfoDto(Table table) {
        InfoDto dto = new InfoDto();
        dto.setLastCard(table.getLastCardInDiscard());
        dto.setMessage(table.getActivePlayer().getName() + "'s turn"); 
        return dto;
    }

    private ReplyDto getStartGameReplyDto(Table table, String player) {
        ReplyDto dto = new ReplyDto();
        dto.setHand(table.getPlayersHandCards(player));
        dto.setMessage("Game Started");
        return dto;
    }
    
    private ReplyDto getWaitDto() {
        ReplyDto dto = new ReplyDto();
        dto.setMessage("Waiting for other users");
        return dto;
    }

}
