package com.valeriisosliuk.service.handler;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.valeriisosliuk.util.TestUtil.*;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.PlayerDetail;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;


public class PickHandlerTest {
	
	@Test
	public void testPickCard() {
		Table table = getStartedTableFourPlayers();
		Player currentPlayer = table.getActivePlayer();
		assertTrue(currentPlayer.isPickAllowed());
		assertEquals(4, currentPlayer.getHand().getCards().size());
		ActionDto action = new ActionDto();
		action.setType(ActionType.PICK);
		
		PickHandler handler = new PickHandler();
		ActionResult result = handler.handle(action, table);
		assertEquals(5, currentPlayer.getHand().getCards().size());
		assertEquals(1, result.getGeneralUpdates().size());
		assertEquals("Homer Picked a card", result.getGeneralUpdates().get(0).getMessages().get(0));
		PlayerDetail playerDetail = result.getGeneralUpdates().get(0).getPlayerUpdate();
		assertEquals("Homer", playerDetail.getName());
		assertEquals(5, playerDetail.getCardCount());
	}

}
