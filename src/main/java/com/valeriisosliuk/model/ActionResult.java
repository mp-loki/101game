package com.valeriisosliuk.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.ResponseDto;

public class ActionResult {
	
	private Map<String, ResponseDto> playerUpdates;
	private List<BroadcastDto> generalUpdates;
	
	public Map<String, ResponseDto> getPlayerUpdates() {
		if (playerUpdates == null) {
			playerUpdates = new HashMap<>();
		}
		return playerUpdates;
	}
	public void setPlayerUpdates(Map<String, ResponseDto> playerUpdates) {
		this.playerUpdates = playerUpdates;
	}
	public List<BroadcastDto> getGeneralUpdates() {
		if (generalUpdates == null) {
			generalUpdates = new LinkedList<BroadcastDto>();
		}
		return generalUpdates;
	}
	public void setGeneralUpdates(List<BroadcastDto> generalUpdates) {
		this.generalUpdates = generalUpdates;
	}
}
