package com.valeriisosliuk.game.state.initializer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.Game;

@Component("demandSuitStateInitializer")
public class DemandSuitStateInitializer implements StateInitinalizer {

    private static final Logger log = Logger.getLogger(DemandSuitStateInitializer.class);
    @Override
    public void initializeState(Game game) {
        // TODO Add sending message to user logic
        log.info("Sending suite demand message to " + game.getActivePlayer().getName());
    }

}
