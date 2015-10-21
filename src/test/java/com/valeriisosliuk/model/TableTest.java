package com.valeriisosliuk.model;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class TableTest {

    private static final String LILLY = "Lilly";
	private static final String MARGE = "Marge";
	private static final String BART = "Bart";
	private static final String HOMER = "Homer";

	@Test
    public void testActiveUser() {
        Table table = getTable();
        Player activePlayer = table.getActivePlayer();
        assertEquals(HOMER, activePlayer.getName());
    }

    @Test
    public void testNextUser() {
        Table table = getTable();
        Player next1 = table.getNextActivePlayer();
        assertEquals(HOMER, next1.getName());
        Player next2 = table.getNextActivePlayer();
        assertEquals(BART, next2.getName());
        Player next3 = table.getNextActivePlayer();
        assertEquals(MARGE, next3.getName());
        Player next4 = table.getNextActivePlayer();
        assertEquals(LILLY, next4.getName());
        Player next5 = table.getNextActivePlayer();
        assertEquals(HOMER, next5.getName());
    }

    @Test
    public void testJoinTwice() {
        Table table = new Table();
        assertTrue(table.joinTable(HOMER));
        assertFalse(table.joinTable(HOMER));
    }
    
    @Test
    public void getSequencedPlayers() {
    	Table table = getTable();
    	String homer = HOMER;
        List<String> sequencedUsers1 = table.getSequencedPlayers(homer);
        assertEquals(3, sequencedUsers1.size());
        assertEquals(BART, sequencedUsers1.get(0));
        assertEquals(MARGE, sequencedUsers1.get(1));
        assertEquals(LILLY, sequencedUsers1.get(2));
        
        String marge = MARGE;
        List<String> sequencedUsers2 = table.getSequencedPlayers(marge);
        assertEquals(3, sequencedUsers2.size());
        assertEquals(LILLY, sequencedUsers2.get(0));
        assertEquals(HOMER, sequencedUsers2.get(1));
        assertEquals(BART, sequencedUsers2.get(2));
    }

    private Table getTable() {
        Table table = new Table();
        table.joinTable(HOMER);
        table.joinTable(BART);
        table.joinTable(MARGE);
        table.joinTable(LILLY);
        return table;
    }
}
