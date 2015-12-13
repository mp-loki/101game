package com.valeriisosliuk.service.handler;

import com.valeriisosliuk.dto.Action;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Table;

public interface ActionHandler {
	
	ActionResult handle(Action action, Table table);

}
