package com.valeriisosliuk.game.config;

import static com.valeriisosliuk.game.model.ActionType.ACTION;
import static com.valeriisosliuk.game.model.ActionType.DEMAND;
import static com.valeriisosliuk.game.model.ActionType.PASS;
import static com.valeriisosliuk.game.model.ActionType.PICK;
import static com.valeriisosliuk.game.model.ActionType.QUIT;
import static com.valeriisosliuk.game.model.ActionType.RESPOND;
import static com.valeriisosliuk.game.model.ActionType.START;
import static com.valeriisosliuk.game.state.State.DEAL_END;
import static com.valeriisosliuk.game.state.State.GAME_START;
import static com.valeriisosliuk.game.state.State.DEAL_START;
import static com.valeriisosliuk.game.state.State.DEMAND_SUIT;
import static com.valeriisosliuk.game.state.State.GAME_OVER;
import static com.valeriisosliuk.game.state.State.INITIAL;
import static com.valeriisosliuk.game.state.State.RESPOND_SUIT;
import static com.valeriisosliuk.game.state.State.TURN_END;
import static com.valeriisosliuk.game.state.State.TURN_IN_PROGRESS;
import static com.valeriisosliuk.game.state.State.TURN_START;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.valeriisosliuk.game.model.ActionType;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.actionhandler.ActionHandler;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;

@Configuration
public class AppConfig {
    
	@Resource
	private StateInitinalizer gameOverStateInitializer;
    @Resource
    private StateInitinalizer initialStateInitializer;
    @Resource
    private StateInitinalizer dealStartStateInitializer;
    @Resource
    private StateInitinalizer turnStartStateInitializer;
    @Resource
    private StateInitinalizer turnEndStateInitializer;
    @Resource
    private StateInitinalizer demandSuitStateInitializer;
    @Resource
    private StateInitinalizer respondSuitStateInitializer;
    @Resource
    private StateInitinalizer dealEndStateInitializer;
    @Resource
    private StateInitinalizer stubStateInitializer;
    @Resource
    private ActionHandler gameStartActionHandler;
    @Resource
    private ActionHandler turnEndActionHandler;
    @Resource
    private ActionHandler pickActionHandler;
    @Resource
    private ActionHandler cardMoveActionHandler;
    @Resource
    private ActionHandler demandSuitActionHandler;
    @Resource
    private ActionHandler respondSuitActionHandler;
    @Resource
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
        stateInitializers.put(GAME_START, stubStateInitializer);
        return stateInitializers;
    }
    
    @Bean(name="actionHandlers")
    public Map<ActionType, ActionHandler> getActionHandlers() {
    	Map<ActionType, ActionHandler> actionHandlers = new HashMap<>();
    	actionHandlers.put(START, gameStartActionHandler);
    	actionHandlers.put(PASS, turnEndActionHandler);
    	actionHandlers.put(PICK, pickActionHandler);
    	actionHandlers.put(ACTION, cardMoveActionHandler);
    	actionHandlers.put(DEMAND, demandSuitActionHandler);
    	actionHandlers.put(RESPOND, respondSuitActionHandler);
    	actionHandlers.put(QUIT, quitActionHandler);
    	return actionHandlers;
    }
}