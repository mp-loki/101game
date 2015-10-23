package com.valeriisosliuk.service.handler;

import com.valeriisosliuk.dto.ActionDto;
import com.valeriisosliuk.model.ActionResult;
import com.valeriisosliuk.model.Table;

public interface ActionHandler {
	
	ActionResult handle(ActionDto action, Table table);

}
