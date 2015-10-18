package com.valeriisosliuk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.valeriisosliuk.Table;

@Component
public class TableService {

	public List<Table> tables;
	
	private int id = 0;

	@PostConstruct
	public void init() {
		tables = new ArrayList<>();
	}
	
	public Table getCurrentTableForUser(String userName) {
		return getTable((t, u) -> t.isPlayerAtTheTable(u), userName).get();
	}
	
	public Table joinFirstAvailableTable(String username) {
		return getTable((t, m) -> t.getPlayers().size() < m, Table.MAX_PLAYERS_AT_THE_TABLE)
				.orElse(createNewTable(id));
	}

	public Table joinTable(String userName, Integer id) {
		Table table = getTable((t, u) -> t.isPlayerAtTheTable(u), userName).orElse(
				getTable((t, i) -> t.getId() == i, id).orElse(createNewTable(id)));
		table.joinTable(userName);
		return table;
	}

	private Table createNewTable(Integer id) {
		Table table = new Table(id);
		tables.add(table);
		return table;
	}

	public <T> Optional<Table> getTable(BiFunction<Table, T, Boolean> function, T t) {
		return tables.stream().filter(table -> function.apply(table, t)).findFirst();
	}
}
