package com.valeriisosliuk.game.service;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.model.Game;

public interface ActionService {
    void handleAction(Game game, Action action);
}
