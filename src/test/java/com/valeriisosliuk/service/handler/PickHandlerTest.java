package com.valeriisosliuk.service.handler;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.*;
import static com.valeriisosliuk.util.TestUtil.*;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.dto.PlayerInfoDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.ActionType;
import com.valeriisosliuk.model.Player;
import com.valeriisosliuk.model.Table;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class PickHandlerTest {
	
	@Autowired
	PickHandler handler;
	
	@Ignore
	@Test
	public void testPickCard() {
		Table table = getStartedTableFourPlayers();
		Player currentPlayer = table.getActivePlayer();
		assertTrue(currentPlayer.isPickAllowed());
		assertEquals(4, currentPlayer.getHandSize());
		Action action = new Action();
		action.setType(ActionType.PICK);
		
		ActionResult result = handler.handle(action, table);
		assertEquals(5, currentPlayer.getHandSize());
		assertEquals(1, result.getGeneralUpdates().size());
		assertEquals("Homer Picked a card", result.getGeneralUpdates().get(0).getMessages().get(0));
		PlayerInfoDto playerDetail = result.getGeneralUpdates().get(0).getPlayerUpdate();
		assertEquals("Homer", playerDetail.getName());
		assertEquals(5, playerDetail.getCardCount());
	}

}
