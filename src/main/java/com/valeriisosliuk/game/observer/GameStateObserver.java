package com.valeriisosliuk.game.observer;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;

@Component("gameStateObserver")
public class GameStateObserver implements Observer {
    
    private static final Logger log = Logger.getLogger(GameStateObserver.class);
    
    @Resource(name="stateInitializers")
    private Map<State, StateInitinalizer> stateInitializers;
    
    @Override
    public void update(Observable gameObservable, Object state) {
        if (!(state instanceof State)) {
            return;
        }
    	log.info("Game state is set to: " + state);
        Game game = (Game) gameObservable;
        StateInitinalizer stateInitializer = stateInitializers.get(state);
        if (stateInitializer != null) {
        	stateInitializer.initializeState(game);
        } else {
        	log.warn("No state initializer found for state: " + state);
        }
    }

}
