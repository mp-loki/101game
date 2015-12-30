package com.valeriisosliuk.dto;

import java.util.LinkedList;
import java.util.List;

@Deprecated
public class TerminalDto {
	private ResponseDtoType type;
	private List<String> messages;
	private List<PlayerEndDealDto> playerDetails;
	
	public TerminalDto(ResponseDtoType type) {
		this.type = type;
	}
	
	public List<String> getMessages() {
		if (messages == null) {
			messages = new LinkedList<>();
		}
		return messages;
	}


	public void setMessages(List<String> messages) {
		this.messages = messages;
	}


	public List<PlayerEndDealDto> getPlayerDetails() {
		if (playerDetails == null) {
			playerDetails = new LinkedList<>();
		}
		return playerDetails;
	}


	public void setPlayerDetails(List<PlayerEndDealDto> playerDetails) {
		this.playerDetails = playerDetails;
	}


	public ResponseDtoType getType() {
		return type;
	}
}
