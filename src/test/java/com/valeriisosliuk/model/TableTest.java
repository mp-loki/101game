package com.valeriisosliuk.model;

import static com.valeriisosliuk.util.TestUtil.BART;
import static com.valeriisosliuk.util.TestUtil.HOMER;
import static com.valeriisosliuk.util.TestUtil.LISA;
import static com.valeriisosliuk.util.TestUtil.MARGE;
import static com.valeriisosliuk.util.TestUtil.getTableFourPlayers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TableTest {

    @Test
    public void testNextUser() {
        Table table = getTableFourPlayers();
        Player next1 = table.getNextActivePlayer();
        assertEquals(HOMER, next1.getName());
        Player next2 = table.getNextActivePlayer();
        assertEquals(BART, next2.getName());
        Player next3 = table.getNextActivePlayer();
        assertEquals(MARGE, next3.getName());
        Player next4 = table.getNextActivePlayer();
        assertEquals(LISA, next4.getName());
        Player next5 = table.getNextActivePlayer();
        assertEquals(HOMER, next5.getName());
    }

    @Test
    public void testJoinTwice() {
        Table table = new Table();
        assertTrue(table.joinTable(HOMER));
        assertFalse(table.joinTable(HOMER));
    }
    
}
