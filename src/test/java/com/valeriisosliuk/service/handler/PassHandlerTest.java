package com.valeriisosliuk.service.handler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static com.valeriisosliuk.util.TestUtil.*;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Table;


public class PassHandlerTest {
	
	@Test
	public void testPass() {
		Table table = getStartedTableFourPlayers();
		assertEquals(HOMER, table.getActivePlayer().getName());
		ActionDto action = new ActionDto();
		action.setType(ActionType.PASS);
		ActionHandler handler = new PassHandler();
		ActionResult result = handler.handle(action, table);
		
		assertEquals(BART, table.getActivePlayer().getName());
		assertEquals(2, result.getGeneralUpdates().get(0).getMessages().size());
		assertEquals("Homer Passes", result.getGeneralUpdates().get(0).getMessages().get(0));
		assertEquals("Bart's turn", result.getGeneralUpdates().get(0).getMessages().get(1));
		assertEquals(2, result.getPlayerUpdates().size());
		assertEquals(false, result.getPlayerUpdates().get(HOMER).isActive());
		assertEquals(false, result.getPlayerUpdates().get(HOMER).isPickAllowed());
		assertEquals(true, result.getPlayerUpdates().get(BART).isActive());
		assertEquals(true, result.getPlayerUpdates().get(BART).isPickAllowed());
	}

}
