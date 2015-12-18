package com.valeriisosliuk.config;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static com.valeriisosliuk.game.state.State.*;
import static com.valeriisosliuk.model.ActionType.*;
import static org.junit.Assert.*;

import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.actionhandler.ActionHandler;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;
import com.valeriisosliuk.model.ActionType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.valeriisosliuk.Application.class, loader=AnnotationConfigContextLoader.class)
public class AppConfigTest {
    
    
    @Resource(name="stateInitializers")
    private Map<State, StateInitinalizer> stateInitializers;

    @Resource(name = "actionHandlers")
    private Map<ActionType, ActionHandler> actionHandlers;
    
    @Test
    public void testStateInitializersInjection() {
        assertNotNull(stateInitializers.get(INITIAL));
        assertNotNull(stateInitializers.get(DEAL_START));
        assertNotNull(stateInitializers.get(TURN_START));
        assertNotNull(stateInitializers.get(TURN_IN_PROGRESS));
        assertNotNull(stateInitializers.get(TURN_END));
        assertNotNull(stateInitializers.get(DEMAND_SUIT));
        assertNotNull(stateInitializers.get(RESPOND_SUIT));
        assertNotNull(stateInitializers.get(DEAL_END));
        assertNotNull(stateInitializers.get(GAME_OVER));
    }
    
    @Test
    public void testActionHandlersInjection() {
        assertNotNull(actionHandlers.get(START));
        assertNotNull(actionHandlers.get(END));
        assertNotNull(actionHandlers.get(PICK));
        assertNotNull(actionHandlers.get(MOVE));
        assertNotNull(actionHandlers.get(DEMAND));
        assertNotNull(actionHandlers.get(RESPOND));
        assertNotNull(actionHandlers.get(QUIT));
    }

}
