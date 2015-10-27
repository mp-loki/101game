package com.valeriisosliuk.service.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.TerminalDto;
import com.valeriisosliuk.dto.DtoFactory;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Rank;
import com.valeriisosliuk.model.Table;

@Component
public class DealProcessor {

    private static final int _101 = 101;

	public TerminalDto endDeal(Table table) {
        String dealWinner = table.getPlayers().stream().filter(p -> p.getHand().isEmpty()).findFirst().get().getName();
        table.endCurrentDeal();
        return DtoFactory.getDealTerminalDto(table, "Deal ended", dealWinner + " Ends this deal");
    }

    public TerminalDto endGame(Table table) {
    	Player winner = table.getActivePlayer();
    	for (Player player : table.getPlayers()) {
    		if (player.getTotalPoints() < winner.getTotalPoints()) {
    			winner = player;
    		}
    	}
        // TODO add game end processing
        return DtoFactory.getGameEndedDto(table, "Game Ended", winner.getName() + " wins!");
    }

    public ActionResult startDeal(Table table, List<Card> cards) {
        ActionResult result = new ActionResult();
        table.startNewDeal(cards);
        Player activePlayer = table.getActivePlayer();
        for (Player player : table.getPlayers()) {
            ResponseDto responseDto = getPlayerStartDealDto(player, table, "New Deal started", activePlayer.getName() + "'s turn");
            result.getPlayerUpdates().put(player.getName(), responseDto);
        }
        return result;
    }

    public boolean isDealEnded(Table table) {
        if (table.getPlayers().stream().anyMatch(p -> p.getHand().isEmpty()) && table.getLastCardInDiscard().getRank() != Rank._6) {
            return true;
        }
        return false;
    }

    public boolean isGameEnded(Table table) {
        return (table.getPlayers().stream().anyMatch(p -> p.getTotalPoints() > _101));
    }

    public ResponseDto getPlayerStartDealDto(Player player, Table table, String... messages) {
        ResponseDto dto = DtoFactory.getResponseDto(player, table, messages);
        dto.setPlayerInfo(table.getSequencedPlayers(player.getName()));
        return dto;
    }
}
