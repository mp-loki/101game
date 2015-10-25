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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.dto.ResponseDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Card;
import com.valeriisosliuk.model.Table;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class StartHandlerTest {
	
	@Autowired
	private ActionHandler startHandler;
	
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
		assertEquals(2, result4.getGeneralUpdates().get(0).getMessages().size());
		assertEquals("Game started", result4.getGeneralUpdates().get(0).getMessages().get(0));
		assertEquals("Homer's turn", result4.getGeneralUpdates().get(0).getMessages().get(1));
		assertEquals(4, result4.getPlayerUpdates().size());
		
		ResponseDto responseDto1 = result4.getPlayerUpdates().get(HOMER);
		ResponseDto responseDto2 = result4.getPlayerUpdates().get(BART);
		ResponseDto responseDto3 = result4.getPlayerUpdates().get(MARGE);
		ResponseDto responseDto4 = result4.getPlayerUpdates().get(LISA);
		
		assertEquals(4, responseDto1.getHand().size());
		assertEquals(4, responseDto2.getHand().size());
		assertEquals(4, responseDto3.getHand().size());
		assertEquals(4, responseDto4.getHand().size());
		
		Card lastCard = table.getLastCardInDiscard();
		
		assertEquals(lastCard, responseDto1.getLastCard());
		assertEquals(lastCard, responseDto2.getLastCard());
		assertEquals(lastCard, responseDto3.getLastCard());
		assertEquals(lastCard, responseDto4.getLastCard());
		
		assertTrue(responseDto1.isPickAllowed());
		assertTrue(responseDto1.isActive());
		
		assertFalse(responseDto2.isPickAllowed());
		assertFalse(responseDto2.isActive());
		assertFalse(responseDto3.isPickAllowed());
		assertFalse(responseDto3.isActive());
		assertFalse(responseDto4.isPickAllowed());
		assertFalse(responseDto4.isActive());
		
		assertEquals(3, responseDto1.getPlayerDetails().size());
		assertEquals(3, responseDto2.getPlayerDetails().size());
		assertEquals(3, responseDto3.getPlayerDetails().size());
		assertEquals(3, responseDto4.getPlayerDetails().size());
		
		assertEquals(BART, responseDto1.getPlayerDetails().get(0).getName());
		assertEquals(4, responseDto1.getPlayerDetails().get(0).getCardCount());
		assertEquals(MARGE, responseDto1.getPlayerDetails().get(1).getName());
		assertEquals(4, responseDto1.getPlayerDetails().get(1).getCardCount());
		assertEquals(LISA, responseDto1.getPlayerDetails().get(2).getName());
		assertEquals(4, responseDto1.getPlayerDetails().get(2).getCardCount());
		
		assertEquals(MARGE, responseDto2.getPlayerDetails().get(0).getName());
		assertEquals(4, responseDto2.getPlayerDetails().get(0).getCardCount());
		assertEquals(LISA, responseDto2.getPlayerDetails().get(1).getName());
		assertEquals(4, responseDto2.getPlayerDetails().get(1).getCardCount());
		assertEquals(HOMER, responseDto2.getPlayerDetails().get(2).getName());
		assertEquals(4, responseDto2.getPlayerDetails().get(2).getCardCount());
		
		
		assertEquals(LISA, responseDto3.getPlayerDetails().get(0).getName());
		assertEquals(4, responseDto3.getPlayerDetails().get(0).getCardCount());
		assertEquals(HOMER, responseDto3.getPlayerDetails().get(1).getName());
		assertEquals(4, responseDto3.getPlayerDetails().get(1).getCardCount());
		assertEquals(BART, responseDto3.getPlayerDetails().get(2).getName());
		assertEquals(4, responseDto3.getPlayerDetails().get(2).getCardCount());
		
		assertEquals(4, responseDto4.getPlayerDetails().get(2).getCardCount());
		assertEquals(HOMER, responseDto4.getPlayerDetails().get(0).getName());
		assertEquals(4, responseDto4.getPlayerDetails().get(0).getCardCount());
		assertEquals(BART, responseDto4.getPlayerDetails().get(1).getName());
		assertEquals(4, responseDto4.getPlayerDetails().get(1).getCardCount());
		assertEquals(MARGE, responseDto4.getPlayerDetails().get(2).getName());
		assertEquals(4, responseDto4.getPlayerDetails().get(2).getCardCount());
	}
	
	private ActionDto getActionDto(String playerName) {
		ActionDto dto = new ActionDto();
		dto.setCurrentPlayer(playerName);
		dto.setType(ActionType.START);
		return dto;
	}
}
