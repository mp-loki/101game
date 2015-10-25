package com.valeriisosliuk.service.handler;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.PlayerDetail;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.dto.DtoFactory;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;
import com.valeriisosliuk.service.handler.validator.ValidatorSupplier;

@Component("cardMoveHandler")
public class CardMoveHandler implements ActionHandler {
	
	@Autowired
	private ValidatorSupplier validatorSupplier;
	
	@Autowired
	private TurnAdvisor turnAdvisor;

	@Override
	public ActionResult handle(ActionDto action, Table table) {
		ActionResult result = new ActionResult();
		Player activePlayer = table.getActivePlayer();
		boolean firstMove = activePlayer.isFirstMove();
		Card lastCard = table.getLastCardInDiscard();
		Card actionCard = action.getCard();
		
		if (!isValid(actionCard, lastCard, firstMove)) {
			result.getPlayerUpdates().put(action.getCurrentPlayer(), getErrorResponeDto(activePlayer, table));
		} else {
			activePlayer.getHand().remove(actionCard);
			activePlayer.setFirstMove(false);
			table.putCardInDiscard(actionCard);
			result.getGeneralUpdates().add(getCardMoveDto(activePlayer, actionCard));
			result.getPlayerUpdates().put(activePlayer.getName(), getHandUpdatedDto(activePlayer, table));
		}
		//TODO check if user has more cards to go or end turn
		
		return result;
	}

	private ResponseDto getHandUpdatedDto(Player activePlayer, Table table) {
		ResponseDto dto = DtoFactory.getResponseDto(activePlayer, table);
		dto.setValidTurnOptions(turnAdvisor.getValidCardsForTurn(activePlayer.getHand().getCards(), 
				table.getLastCardInDiscard(), activePlayer.isFirstMove()));
		return dto;
	}

	private BroadcastDto getCardMoveDto(Player activePlayer, Card actionCard) {
		BroadcastDto dto = new BroadcastDto();
		dto.setLastCard(actionCard);
		dto.setPlayerUpdate(new PlayerDetail(activePlayer.getName(), activePlayer.getHandSize()));
		dto.getMessages().add(activePlayer.getName() + " goes with " + actionCard);
		return dto;
	}

	private ResponseDto getErrorResponeDto(Player player, Table table) {
		ResponseDto dto = DtoFactory.getResponseDto(player, table, "This move is not valid");
		Set<Card> cards = turnAdvisor.getValidCardsForTurn(player.getHand().getCards(), table.getLastCardInDiscard(), player.isFirstMove());
		if (!CollectionUtils.isEmpty(cards)) {
			dto.setValidTurnOptions(cards);
			dto.getMessages().add("Possible moves are: " + cards);
		} else if (player.isPickAllowed()){
			dto.getMessages().add("Pick a card");
		} 
		return dto;
	}

	private boolean isValid(Card card, Card lastCard, boolean firstMove) {
		
		return validatorSupplier.getValidator(lastCard, firstMove).validate(card, lastCard);
	}

}
