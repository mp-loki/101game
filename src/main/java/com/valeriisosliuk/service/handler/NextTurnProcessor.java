package com.valeriisosliuk.service.handler;

import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.valeriisosliuk.dto.BroadcastDto;
import com.valeriisosliuk.dto.DtoFactory;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Rank;
import com.valeriisosliuk.model.Table;

@Component
public class NextTurnProcessor {
	
	public void processNextMove(Table table, ActionResult actionResult) {
		if (isTurnEnded(table)) {
			Player player = table.getActivePlayer();
			Set<Card> cards = player.getCurrentTurnCards();
			player.setCurrentTurnCards(null);
			BroadcastDto dto = DtoFactory.getBroadcastDto(player, table, player.getName() + " ends turn");
			actionResult.getGeneralUpdates().add(dto);
			processCurrentTurnCards(table, cards, actionResult);
		}
	}

	private boolean isTurnEnded(Table table) {
		Player player = table.getActivePlayer();
		if (table.getLastCardInDiscard().getRank() == Rank._6) {
			return false;
		}
		if (CollectionUtils.isEmpty(player.getValidNextTurnOptions())) {
			if (player.isPickAllowed()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private void processCurrentTurnCards(Table table, Set<Card> cards, ActionResult result) {
		if (cards.stream().anyMatch(c -> c.getRank() == Rank.JACK)) {
			processDemandSuiteAction(table, result);
		} else if (cards.stream().anyMatch(c -> c.getRank() == Rank.ACE)) {
			processAceSkipTurn(table, cards, result);
		} else if (cards.stream().anyMatch(c -> c.getRank() == Rank._7)) {
			processSevenCardPickCards(table, cards, result);
		} else if (cards.stream().anyMatch(c -> c.getRank() == Rank._8)) {
			procesEightCardSkipTurns(table, cards, result);
		} else {
			activateNextPlayer(table, result);
		}

	}

	private void procesEightCardSkipTurns(Table table, Set<Card> cards, ActionResult result) {
		Player skippedPlayer = table.skipPlayer();
		long eightsInCurrentTurn = getCardByRankCount(cards, Rank._8);
		for (long i = 0; i < eightsInCurrentTurn * 2; i++) {
			Card card = table.getCardFromDeck();
			skippedPlayer.getHand().add(card);
		}
		String message1 =  skippedPlayer.getName() + " Skips turn because of 8!";
		String message2 =  skippedPlayer.getName() + " gets " + eightsInCurrentTurn * 2 + " cards";
		BroadcastDto broadcastDto = DtoFactory.getBroadcastDto(skippedPlayer, table, skippedPlayer.getName() + message1, message2);
		ResponseDto playerDto = DtoFactory.getResponseDto(skippedPlayer, table);
		result.getGeneralUpdates().add(broadcastDto);
		result.getPlayerUpdates().put(skippedPlayer.getName(), playerDto);

		activateNextPlayer(table, result);
	}

	private void processSevenCardPickCards(Table table, Set<Card> cards, ActionResult result) {
		activateNextPlayer(table, result);
		Player nextActivePlayer = table.getActivePlayer();
		long sevensInCurrentTurn = getCardByRankCount(cards, Rank._7);
		for (long i = 0; i < sevensInCurrentTurn; i++) {
			Card card = table.getCardFromDeck();
			table.getActivePlayer().getHand().add(card);
		}
		String message;
		if (sevensInCurrentTurn == 1) {
			message = "gets a card";
		} else {
			message = "gets " + sevensInCurrentTurn + " cards";
		}
		BroadcastDto broadcastDto = DtoFactory.getBroadcastDto(nextActivePlayer, table, nextActivePlayer.getName()
				+ " " + message);
		ResponseDto playerDto = DtoFactory.getResponseDto(nextActivePlayer, table);
		result.getGeneralUpdates().add(broadcastDto);
		result.getPlayerUpdates().put(nextActivePlayer.getName(), playerDto);
	}

	private void processAceSkipTurn(Table table, Set<Card> cards, ActionResult result) {
		Player skippedPlayer = table.skipPlayer();
		BroadcastDto dto = DtoFactory.getBroadcastDto(skippedPlayer, table, skippedPlayer.getName()
				+ " skips turn because of Ace!");
		result.getGeneralUpdates().add(dto);
		activateNextPlayer(table, result);
	}

	private long getCardByRankCount(Set<Card> cards, Rank rank) {
		return cards.stream().filter(c -> c.getRank() == rank).count();
	}

	private void processDemandSuiteAction(Table table, ActionResult result) {
		// TODO implement demand suite on ui and server side
		// just moving to next user for now
		activateNextPlayer(table, result);
	}

	private void activateNextPlayer(Table table, ActionResult actionResult) {
		if (table.getActivePlayer().getHand().isEmpty()) {
			return;
		}
		Player currentPlayer = table.getActivePlayer();
		Player nextActivePlayer = table.getNextActivePlayer();
		ResponseDto currentPlayerDto = DtoFactory.getResponseDto(currentPlayer, table);
		ResponseDto nextPlayerDto = DtoFactory.getResponseDto(nextActivePlayer, table);
		BroadcastDto broadcastDto = DtoFactory.getBroadcastDto(nextActivePlayer, table, nextActivePlayer.getName()
				+ "'s turn");
		actionResult.getGeneralUpdates().add(broadcastDto);
		actionResult.getPlayerUpdates().put(currentPlayer.getName(), currentPlayerDto);
		actionResult.getPlayerUpdates().put(nextActivePlayer.getName(), nextPlayerDto);
	}
}
