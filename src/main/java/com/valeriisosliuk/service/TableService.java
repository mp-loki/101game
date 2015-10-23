package com.valeriisosliuk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.InfoDto;
import com.valeriisosliuk.dto.StateDto;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Hand;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;

@Component
public class TableService {

	public List<Table> tables;
	
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
		return getTable((t, m) -> t.getPlayers().size() < m, Table.MAX_PLAYERS_AT_THE_TABLE)
				.orElse(createNewTable());
	}

	public Table joinTable(String playerName) {
		Table table = getTable((t, u) -> t.isPlayerAtTheTable(u), playerName).orElse(joinFirstAvailableTable(playerName));
		table.joinTable(playerName);
		return table;
	}

	private Table createNewTable() {
		Table table = new Table();
		tables.add(table);
		return table;
	}

	public <T> Optional<Table> getTable(BiFunction<Table, T, Boolean> function, T t) {
		return tables.stream().filter(table -> function.apply(table, t)).findFirst();
	}

    public void tryStart(String currentPlayerName) {
        Table table = getCurrentTableForplayer(currentPlayerName);
        boolean started = table.start(currentPlayerName);
        
        if (!started) {
            StateDto StateDto = getWaitDto(currentPlayerName);
            messageService.send(currentPlayerName, StateDto);
        } else {
            for (String player : table.getPlayers()) {
                messageService.send(player, getStateDto(player));
            }
            messageService.sendToAll(getStartGameInfoDto(table));
        }
    }
    
    public void tryPickCard(String currentPlayerName) {
        Table table = getCurrentTableForplayer(currentPlayerName);
        if (table.isStarted() && table.getActivePlayer().getName().equals(currentPlayerName)) {
            Card card = table.getCardFromDeck().orElse(this.turnoverAndGetCard(table));
            Player currentPlayer = table.getActivePlayer();
            Hand hand = currentPlayer.getHand();
            currentPlayer.setPickAllowed(false);
            hand.add(card);
            StateDto playerStateUpdate = new StateDto();
            playerStateUpdate.setHand(hand.getCards());
            playerStateUpdate.setPickAllowed(false);
            messageService.send(currentPlayerName, playerStateUpdate);
        }
    }

    private Card turnoverAndGetCard(Table table) {
        if (!table.cardDeckHasNext()) {
            table.turnOver();
            StateDto dto = new StateDto();
            dto.setMessage("Deck is turned over. Points doubled");
            dto.setLastCard(table.getLastCardInDiscard());
            messageService.sendToAll(dto);
        }
        return table.getCardFromDeck().get();
    }

    private InfoDto getStartGameInfoDto(Table table) {
        InfoDto dto = new InfoDto();
        dto.setLastCard(table.getLastCardInDiscard());
        String activePlayer = table.getActivePlayer().getName();
        dto.setActivePlayer(activePlayer);
        dto.setMessage(activePlayer + "'s turn"); 
        dto.setCardsNumPerPlayer(table.getCardNumsPerPlayer());
        return dto;
    }   

	private StateDto getWaitDto(String currentPlayerName) {
        StateDto dto = new StateDto();
        dto.setCurrentPlayerName(currentPlayerName);
        dto.setMessage("Waiting for other players");
        return dto;
    }

    public StateDto getStateDto(String playerName) {
        StateDto dto = new StateDto();
        dto.setCurrentPlayerName(playerName);
        Table table = joinTable(playerName);
        
        if (table.isStarted()) {
            dto.setLastCard(table.getLastCardInDiscard());
            dto.setHand(table.getPlayer(playerName).getHand().getCards());
            dto.setPlayers(table.getSequencedPlayersList(playerName));
            dto.setPickAllowed(table.isActivePlayer(playerName));
        } else {
            dto.setMessage("Waiting for players");
        }
        return dto;
    }
}
