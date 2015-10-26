package com.valeriisosliuk.service.handler;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.valeriisosliuk.dto.TerminalDto;
import com.valeriisosliuk.dto.DtoFactory;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Rank;
import com.valeriisosliuk.model.Table;

@Component
public class DealProcessor {

    public TerminalDto endDeal(Table table) {
        String dealWinner = table.getPlayers().stream().filter(p -> p.getHand().isEmpty()).findFirst().get().getName();
        table.endCurrentDeal();
        return DtoFactory.getDealEndedDto(table, "Deal ended", dealWinner + " Ends this deal");
    }

    public TerminalDto endGame(Table table) {
        // TODO add game end processing
        return DtoFactory.getGameEndedDto(table, "Game Ended");
    }

    public ActionResult startDeal(Table table) {
        ActionResult result = new ActionResult();
        table.startNewDeal();
        Player activePlayer = table.getActivePlayer();
        for (Player player : table.getPlayers()) {
            //ResponseDto responseDto = DtoFactory.getResponseDto(player, table, "New Deal started", activePlayer.getName() + "'s turn");
            ResponseDto responseDto = getPlayerStartDealDto(player, table, "New Deal started", activePlayer.getName() + "'s turn");
            result.getPlayerUpdates().put(player.getName(), responseDto);
        }
        return result;
    }

    public boolean isDealEnded(Table table) {
        if (CollectionUtils.isEmpty(table.getActivePlayer().getHand()) && table.getLastCardInDiscard().getRank() != Rank._6) {
            return true;
        }
        return false;
    }

    public boolean isGameEnded(Table table) {
        return (table.getPlayers().stream().anyMatch(p -> p.getTotalPoints() > 101));
    }

    public ResponseDto getPlayerStartDealDto(Player player, Table table, String... messages) {
        ResponseDto dto = DtoFactory.getResponseDto(player, table, messages);
        dto.setPlayerInfo(table.getSequencedPlayersList(player.getName()));
        return dto;
    }

}
