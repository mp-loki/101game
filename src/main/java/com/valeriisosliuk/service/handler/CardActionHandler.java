package com.valeriisosliuk.service.handler;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Table;

@Component("cardActionHandler")
public class CardActionHandler implements ActionHandler{

	@Override
	public ActionResult handle(Action action, Table table) {
		ActionResult result = new ActionResult();
		Card actionCard = action.getCard();
		Card lastCard = table.getLastCardInDiscard();
		if (!isValid(actionCard, lastCard)) {
			result.getPlayerUpdates().put(action.getPlayerName(), getInvalidCardActionDto());
		}
		// TODO Auto-generated method stub
		return result;
	}

	private ResponseDto getInvalidCardActionDto() {
		ResponseDto dto = new ResponseDto();
		dto.getMessages().add("This move is invalid");
		return dto;
	}

	private boolean isValid(Card actionCard, Card lastCard) {
		// TODO Auto-generated method stub
		return true;
	}

}
