package com.valeriisosliuk.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TableTest {

    @Test
    public void testActiveUser() {
        Table table = getTable();
        Player activePlayer = table.getActivePlayer();
        assertEquals("Homer", activePlayer.getName());
    }

    @Test
    public void testNextUser() {
        Table table = getTable();
        Player next1 = table.getNextActivePlayer();
        assertEquals("Homer", next1.getName());
        Player next2 = table.getNextActivePlayer();
        assertEquals("Bart", next2.getName());
        Player next3 = table.getNextActivePlayer();
        assertEquals("Marge", next3.getName());
        Player next4 = table.getNextActivePlayer();
        assertEquals("Lilly", next4.getName());
        Player next5 = table.getNextActivePlayer();
        assertEquals("Homer", next5.getName());
    }

    @Test
    public void testJoinTwice() {
        Table table = new Table(0);
        assertTrue(table.joinTable("Homer"));
        assertFalse(table.joinTable("Homer"));
    }

    private Table getTable() {
        Table table = new Table(1);
        table.joinTable("Homer");
        table.joinTable("Bart");
        table.joinTable("Marge");
        table.joinTable("Lilly");
        return table;
    }
}
