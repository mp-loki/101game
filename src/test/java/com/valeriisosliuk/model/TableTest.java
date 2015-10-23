package com.valeriisosliuk.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;

public class TableTest {

    private static final String LILLY = "Lilly";
	private static final String MARGE = "Marge";
	private static final String BART = "Bart";
	private static final String HOMER = "Homer";


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

    private Table getTable() {
        Table table = new Table();
        table.joinTable(HOMER);
        table.joinTable(BART);
        table.joinTable(MARGE);
        table.joinTable(LILLY);
        return table;
    }
    
}
