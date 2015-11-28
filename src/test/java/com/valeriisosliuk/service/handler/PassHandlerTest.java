package com.valeriisosliuk.service.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;
import static com.valeriisosliuk.util.TestUtil.*;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Table;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class PassHandlerTest {
	
	@Autowired
	PassHandler handler;
	@Test
	public void testPass() {
		Table table = getStartedTableFourPlayers();
		assertEquals(HOMER, table.getActivePlayer().getName());
		Action action = new Action();
		action.setType(ActionType.END);
		ActionResult result = handler.handle(action, table);
		
		assertEquals(BART, table.getActivePlayer().getName());
		assertEquals(2, result.getGeneralUpdates().size());
		assertEquals(1, result.getGeneralUpdates().get(0).getMessages().size());
		assertEquals(1, result.getGeneralUpdates().get(1).getMessages().size());
		assertEquals("Homer Passes", result.getGeneralUpdates().get(0).getMessages().get(0));
		assertEquals("Bart's turn", result.getGeneralUpdates().get(1).getMessages().get(0));
		assertEquals(2, result.getPlayerUpdates().size());
		assertEquals(false, result.getPlayerUpdates().get(HOMER).isActive());
		assertEquals(false, result.getPlayerUpdates().get(HOMER).isPickAllowed());
		assertEquals(true, result.getPlayerUpdates().get(BART).isActive());
		assertEquals(true, result.getPlayerUpdates().get(BART).isPickAllowed());
	}

}
