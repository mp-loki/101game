package com.valeriisosliuk.dto;


import java.util.Arrays;
import java.util.Set;

import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;

public class DtoFactory {
	
	public static ResponseDto getResponseDto(Player player, Table table, String... messages) {
		ResponseDto dto = new ResponseDto();
		dto.setCurrentPlayerName(player.getName());
		dto.setHand(player.getHand());
		dto.setValidTurnOptions(player.getValidNextTurnOptions());
		dto.setLastCard(table.getLastCardInDiscard());
		dto.setPickAllowed(player.isPickAllowed());
		dto.setEndTurnAllowed(player.isEndTurnAllowed());
		dto.setActive(table.isActivePlayer(player));
		dto.getMessages().addAll(Arrays.asList(messages));
		dto.setActive(table.isActivePlayer(player));
		return dto;
	}
	
	public static BroadcastDto getBroadcastDto(Player player, Table table, String... messages) {
		BroadcastDto dto = new BroadcastDto();
		dto.setLastCard(table.getLastCardInDiscard());
		dto.setPlayerUpdate(new PlayerInfoDto(player.getName(), player.getHandSize(), player.getTotalPoints()));
		dto.getMessages().addAll(Arrays.asList(messages));
		return dto;
	}
	
	public static TerminalDto getDealTerminalDto(Table table, String... messages) {
		TerminalDto dto = new TerminalDto(ResponseDtoType.DEAL_OVER);
		dto.setMessages(Arrays.asList(messages));
		table.getPlayers().stream().map(p -> getPlayerEndDealDto(p)).forEach(pDto -> dto.getPlayerDetails().add(pDto));
		return dto;
	}
	
	private static PlayerEndDealDto getPlayerEndDealDto(Player player) {
		PlayerEndDealDto playerDto = new PlayerEndDealDto();
		playerDto.setName(player.getName());
		playerDto.setTotalPoints(player.getTotalPoints());
		Set<Card> hand = player.getHand();
		playerDto.setHand(hand);
		return playerDto;
	}
	
	public static TerminalDto getGameEndedDto(Table table, String... messages) {
		TerminalDto dto = new TerminalDto(ResponseDtoType.GAME_OVER);
		dto.setMessages(Arrays.asList(messages));
		return dto;
	}

}
