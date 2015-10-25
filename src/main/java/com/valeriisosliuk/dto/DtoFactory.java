package com.valeriisosliuk.dto;


import java.util.Arrays;

import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;

public class DtoFactory {
	
	public static ResponseDto getResponseDto(Player player, Table table, String... messages) {
		ResponseDto dto = new ResponseDto();
		dto.setCurrentPlayerName(player.getName());
		dto.setHand(player.getHand().getCards());
		dto.setLastCard(table.getLastCardInDiscard());
		dto.setPickAllowed(player.isPickAllowed());
		dto.setEndTurnAllowed(player.isEndTurnAllowed());
		dto.setActive(table.isActivePlayer(player));
		dto.getMessages().addAll(Arrays.asList(messages));
		dto.setActive(table.isActivePlayer(player));
		return dto;
	}

}
