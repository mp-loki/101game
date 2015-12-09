package com.valeriisosliuk.game.state.initializer;

import org.apache.log4j.Logger;

import com.valeriisosliuk.game.Game;

public class DemandSuitInitializer implements StateInitinalizer {

    private static final Logger log = Logger.getLogger(DemandSuitInitializer.class);
    @Override
    public void initializeState(Game game) {
        // TODO Add sending message to user logic
        log.info("Sending suite demand message to " + game.getActivePlayer().getName());
    }

}
