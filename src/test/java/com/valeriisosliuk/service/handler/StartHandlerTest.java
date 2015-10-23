package com.valeriisosliuk.service.handler;

import static com.valeriisosliuk.util.TestUtil.BART;
import static com.valeriisosliuk.util.TestUtil.HOMER;
import static com.valeriisosliuk.util.TestUtil.LISA;
import static com.valeriisosliuk.util.TestUtil.MARGE;
import static com.valeriisosliuk.util.TestUtil.getTableFourPlayers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Table;

public class StartHandlerTest {
	
	private ActionHandler startHandler = new StartHandler();
	
	@Test
	public void testStartHandler() {
		Table table = getTableFourPlayers();
		ActionDto dto1 = getActionDto(HOMER);
		ActionDto dto2 = getActionDto(BART);
		ActionDto dto3 = getActionDto(MARGE);
		ActionDto dto4 = getActionDto(LISA);
		
		ActionResult result1 = startHandler.handle(dto1, table);
		assertFalse("Table was started when it did not suppose to", table.isStarted());
		assertEquals(0, result1.getGeneralUpdates().size());
		assertEquals(1, result1.getPlayerUpdates().size());
		assertEquals("Waiting for other players", result1.getPlayerUpdates().get(HOMER).getMessages().get(0));
		
		ActionResult result2 = startHandler.handle(dto2, table);
		assertFalse("Table was started when it did not suppose to", table.isStarted());
		assertEquals(0, result2.getGeneralUpdates().size());
		assertEquals(1, result2.getPlayerUpdates().size());
		assertEquals("Waiting for other players", result2.getPlayerUpdates().get(BART).getMessages().get(0));
		ActionResult result3 = startHandler.handle(dto3, table);
		assertFalse("Table was started when it did not suppose to", table.isStarted());
		assertEquals(0, result3.getGeneralUpdates().size());
		assertEquals(1, result3.getPlayerUpdates().size());
		assertEquals("Waiting for other players", result3.getPlayerUpdates().get(MARGE).getMessages().get(0));
		ActionResult result4 = startHandler.handle(dto4, table);
		assertTrue("Table did not start when it should", table.isStarted());
		assertEquals(1, result4.getGeneralUpdates().size());
		assertEquals(1, result4.getGeneralUpdates().get(0).getMessages().size());
		assertEquals("Homer's turn", result4.getGeneralUpdates().get(0).getMessages().get(0));
		assertEquals(4, result4.getPlayerUpdates().size());
		
		ResponseDto stateDto1 = result4.getPlayerUpdates().get(HOMER);
		ResponseDto stateDto2 = result4.getPlayerUpdates().get(BART);
		ResponseDto stateDto3 = result4.getPlayerUpdates().get(MARGE);
		ResponseDto stateDto4 = result4.getPlayerUpdates().get(LISA);
		
		assertEquals("Game started", stateDto1.getMessages().get(0));
		assertEquals("Game started", stateDto2.getMessages().get(0));
		assertEquals("Game started", stateDto3.getMessages().get(0));
		assertEquals("Game started", stateDto4.getMessages().get(0));
		
		assertEquals(4, stateDto1.getHand().size());
		assertEquals(4, stateDto2.getHand().size());
		assertEquals(4, stateDto3.getHand().size());
		assertEquals(4, stateDto4.getHand().size());
		
		Card lastCard = table.getLastCardInDiscard();
		
		assertEquals(lastCard, stateDto1.getLastCard());
		assertEquals(lastCard, stateDto2.getLastCard());
		assertEquals(lastCard, stateDto3.getLastCard());
		assertEquals(lastCard, stateDto4.getLastCard());
		
		assertTrue(stateDto1.isPickAllowed());
		assertTrue(stateDto1.isActive());
		
		assertFalse(stateDto2.isPickAllowed());
		assertFalse(stateDto2.isActive());
		assertFalse(stateDto3.isPickAllowed());
		assertFalse(stateDto3.isActive());
		assertFalse(stateDto4.isPickAllowed());
		assertFalse(stateDto4.isActive());
		
		assertEquals(3, stateDto1.getPlayerDetails().size());
		assertEquals(3, stateDto2.getPlayerDetails().size());
		assertEquals(3, stateDto3.getPlayerDetails().size());
		assertEquals(3, stateDto4.getPlayerDetails().size());
		
		assertEquals(BART, stateDto1.getPlayerDetails().get(0).getName());
		assertEquals(4, stateDto1.getPlayerDetails().get(0).getCardCount());
		assertEquals(MARGE, stateDto1.getPlayerDetails().get(1).getName());
		assertEquals(4, stateDto1.getPlayerDetails().get(1).getCardCount());
		assertEquals(LISA, stateDto1.getPlayerDetails().get(2).getName());
		assertEquals(4, stateDto1.getPlayerDetails().get(2).getCardCount());
		
		assertEquals(MARGE, stateDto2.getPlayerDetails().get(0).getName());
		assertEquals(4, stateDto2.getPlayerDetails().get(0).getCardCount());
		assertEquals(LISA, stateDto2.getPlayerDetails().get(1).getName());
		assertEquals(4, stateDto2.getPlayerDetails().get(1).getCardCount());
		assertEquals(HOMER, stateDto2.getPlayerDetails().get(2).getName());
		assertEquals(4, stateDto2.getPlayerDetails().get(2).getCardCount());
		
		
		assertEquals(LISA, stateDto3.getPlayerDetails().get(0).getName());
		assertEquals(4, stateDto3.getPlayerDetails().get(0).getCardCount());
		assertEquals(HOMER, stateDto3.getPlayerDetails().get(1).getName());
		assertEquals(4, stateDto3.getPlayerDetails().get(1).getCardCount());
		assertEquals(BART, stateDto3.getPlayerDetails().get(2).getName());
		assertEquals(4, stateDto3.getPlayerDetails().get(2).getCardCount());
		
		assertEquals(4, stateDto4.getPlayerDetails().get(2).getCardCount());
		assertEquals(HOMER, stateDto4.getPlayerDetails().get(0).getName());
		assertEquals(4, stateDto4.getPlayerDetails().get(0).getCardCount());
		assertEquals(BART, stateDto4.getPlayerDetails().get(1).getName());
		assertEquals(4, stateDto4.getPlayerDetails().get(1).getCardCount());
		assertEquals(MARGE, stateDto4.getPlayerDetails().get(2).getName());
		assertEquals(4, stateDto4.getPlayerDetails().get(2).getCardCount());
	}
	
	private ActionDto getActionDto(String playerName) {
		ActionDto dto = new ActionDto();
		dto.setCurrentPlayer(playerName);
		dto.setType(ActionType.START);
		return dto;
	}
}
