package com.valeriisosliuk.util;

import com.valeriisosliuk.model.Table;

public class TestUtil {
	
	public static final String LISA = "Lisa";
	public static final String MARGE = "Marge";
	public static final String BART = "Bart";
	public static final String HOMER = "Homer";

	public static Table getTableFourPlayers() {
		Table table = new Table();
		table.joinTable(HOMER);
		table.joinTable(BART);
		table.joinTable(MARGE);
		table.joinTable(LISA);
		return table;
	}
	public static Table getStartedTableFourPlayers() {
		Table table = getTableFourPlayers();
		table.start(HOMER);
		table.start(BART);
		table.start(MARGE);
		table.start(LISA);
		return table;
	}

}
