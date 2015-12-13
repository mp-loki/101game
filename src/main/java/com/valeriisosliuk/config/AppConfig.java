package com.valeriisosliuk.config;

import static com.valeriisosliuk.game.state.State.DEAL_END;
import static com.valeriisosliuk.game.state.State.DEAL_START;
import static com.valeriisosliuk.game.state.State.DEMAND_SUIT;
import static com.valeriisosliuk.game.state.State.GAME_OVER;
import static com.valeriisosliuk.game.state.State.INITIAL;
import static com.valeriisosliuk.game.state.State.RESPOND_SUIT;
import static com.valeriisosliuk.game.state.State.TURN_END;
import static com.valeriisosliuk.game.state.State.TURN_IN_PROGRESS;
import static com.valeriisosliuk.game.state.State.TURN_START;
import static com.valeriisosliuk.model.ActionType.DEMAND;
import static com.valeriisosliuk.model.ActionType.END;
import static com.valeriisosliuk.model.ActionType.MOVE;
import static com.valeriisosliuk.model.ActionType.PICK;
import static com.valeriisosliuk.model.ActionType.QUIT;
import static com.valeriisosliuk.model.ActionType.RESPOND;
import static com.valeriisosliuk.model.ActionType.START;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.actionhandler.ActionHandler;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;
import com.valeriisosliuk.model.ActionType;

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
    @Autowired
    private ActionHandler gameStartActionHandler;
    @Autowired
    private ActionHandler turnEndActionHandler;
    @Autowired
    private ActionHandler pickActionHandler;
    @Autowired
    private ActionHandler cardMoveActionHandler;
    @Autowired
    private ActionHandler demandSuitActionHandler;
    @Autowired
    private ActionHandler respondSuitActionHandler;
    @Autowired
    private ActionHandler quitActionHandler;

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
    
    @Bean(name="actionHandlers")
    public Map<ActionType, ActionHandler> getActionHandlers() {
    	Map<ActionType, ActionHandler> actionHandlers = new HashMap<>();
    	actionHandlers.put(START, gameStartActionHandler);
    	actionHandlers.put(END, turnEndActionHandler);
    	actionHandlers.put(PICK, pickActionHandler);
    	actionHandlers.put(MOVE, cardMoveActionHandler);
    	actionHandlers.put(DEMAND, demandSuitActionHandler);
    	actionHandlers.put(RESPOND, respondSuitActionHandler);
    	actionHandlers.put(QUIT, quitActionHandler);
    	return actionHandlers;
    }
}
