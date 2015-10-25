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
import com.valeriisosliuk.model.Rank;
import com.valeriisosliuk.model.Table;
import com.valeriisosliuk.service.handler.validator.ValidatorSupplier;

@Component("cardMoveHandler")
public class CardMoveHandler implements ActionHandler {

	@Autowired
	private ValidatorSupplier validatorSupplier;

	@Autowired
	private TurnAdvisor turnAdvisor;

	@Autowired
	private NextTurnProcessor nextTurnProcessor;

	@Override
	public ActionResult handle(ActionDto action, Table table) {
		ActionResult result = new ActionResult();
		if (table.isActivePlayer(action.getCurrentPlayer())) {
			Player activePlayer = table.getActivePlayer();
			Card lastCard = table.getLastCardInDiscard();
			Card actionCard = action.getCard();
			boolean firstMove = activePlayer.isFirstMove();

			if (!isValid(actionCard, lastCard, firstMove)) {
				result.getPlayerUpdates().put(action.getCurrentPlayer(), getErrorResponeDto(activePlayer, table));
			} else {
				processCardMove(activePlayer, table, actionCard, result);
				nextTurnProcessor.processNextMove(table, result);
			}
		}
		return result;
	}

	private void processCardMove(Player player, Table table, Card actionCard, ActionResult result) {
		player.getHand().remove(actionCard);
		player.getCurrentTurnCards().add(actionCard);
		player.setFirstMove(false);
		table.putCardInDiscard(actionCard);
		if (actionCard.getRank() == Rank._6) {
			player.setPickAllowed(false);
		} else {
			player.setPickAllowed(false);
		}
		player.setValidNextMoveOptions(turnAdvisor.getValidCardsForTurn(player.getHand(), table.getLastCardInDiscard(),
				player.isFirstMove()));
		result.getGeneralUpdates().add(getCardMoveDto(player, table));
		result.getPlayerUpdates().put(player.getName(), getHandUpdatedDto(player, table));
	}

	private ResponseDto getHandUpdatedDto(Player activePlayer, Table table) {
		return DtoFactory.getResponseDto(activePlayer, table);
	}

	private BroadcastDto getCardMoveDto(Player activePlayer, Table table) {
		BroadcastDto dto = DtoFactory.getBroadcastDto(activePlayer, table, activePlayer.getName() + " goes with "
				+ table.getLastCardInDiscard());
		return dto;
	}

	private ResponseDto getErrorResponeDto(Player player, Table table) {
		ResponseDto dto = DtoFactory.getResponseDto(player, table, "This move is not valid");
		Set<Card> cards = turnAdvisor.getValidCardsForTurn(player.getHand(), table.getLastCardInDiscard(),
				player.isFirstMove());
		if (!CollectionUtils.isEmpty(cards)) {
			dto.setValidTurnOptions(cards);
			dto.getMessages().add("Possible moves are: " + cards);
		} else if (player.isPickAllowed()) {
			dto.getMessages().add("Pick a card");
		}
		return dto;
	}

	private boolean isValid(Card card, Card lastCard, boolean firstMove) {

		return validatorSupplier.getValidator(lastCard, firstMove).validate(card, lastCard);
	}

}
