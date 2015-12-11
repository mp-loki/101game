package com.valeriisosliuk.game.observer;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;

public class GameObserver implements Observer {
    
    private static final Logger log = Logger.getLogger(GameObserver.class);
    
    private Map<State, StateInitinalizer> stateInitializers;
    
    @Override
    public void update(Observable game, Object state) {
        log.info("Game state changed to: " + state); 
        
    }

}
