package com.valeriisosliuk.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.valeriisosliuk.game.state.State;

import static com.valeriisosliuk.game.state.State.*;

import com.valeriisosliuk.game.state.initializer.StateInitinalizer;

@Configuration
public class AppConfig {
    
    @Autowired
    private StateInitinalizer initialStateInitializer;
    @Autowired
    private StateInitinalizer dealStartStateInitializer;
    @Autowired
    private StateInitinalizer turnStartStateInitializer;
    @Autowired
    private StateInitinalizer turnEndStateInitializer;
    @Autowired
    private StateInitinalizer demandSuitStateInitializer;
    @Autowired
    private StateInitinalizer respondSuitStateInitializer;
    @Autowired
    private StateInitinalizer dealEndStateInitializer;
    @Autowired
    private StateInitinalizer gameOverStateInitializer;
    @Autowired
    private StateInitinalizer stubStateInitializer;

    @Bean(name = "stateInitializers")
    public Map<State, StateInitinalizer> getStateInitializers() {
        Map<State, StateInitinalizer> stateInitializers = new HashMap<>();
        stateInitializers.put(INITIAL, initialStateInitializer);
        stateInitializers.put(DEAL_START, dealStartStateInitializer);
        stateInitializers.put(TURN_START, turnStartStateInitializer);
        stateInitializers.put(TURN_IN_PROGRESS, stubStateInitializer);
        stateInitializers.put(TURN_END, turnEndStateInitializer);
        stateInitializers.put(DEMAND_SUIT, demandSuitStateInitializer);
        stateInitializers.put(RESPOND_SUIT, respondSuitStateInitializer);
        stateInitializers.put(DEAL_END, dealEndStateInitializer);
        stateInitializers.put(GAME_OVER, gameOverStateInitializer);
        return stateInitializers;
    }
}
