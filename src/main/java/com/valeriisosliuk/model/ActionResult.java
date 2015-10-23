package com.valeriisosliuk.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.valeriisosliuk.dto.ResponseDto;

public class ActionResult {
	
	private Map<String, ResponseDto> playerUpdates;
	private List<ResponseDto> generalUpdates;
	
	public Map<String, ResponseDto> getPlayerUpdates() {
		if (playerUpdates == null) {
			playerUpdates = new HashMap<>();
		}
		return playerUpdates;
	}
	public void setPlayerUpdates(Map<String, ResponseDto> playerUpdates) {
		this.playerUpdates = playerUpdates;
	}
	public List<ResponseDto> getGeneralUpdates() {
		if (generalUpdates == null) {
			generalUpdates = new LinkedList<ResponseDto>();
		}
		return generalUpdates;
	}
	public void setGeneralUpdates(List<ResponseDto> generalUpdates) {
		this.generalUpdates = generalUpdates;
	}
}
