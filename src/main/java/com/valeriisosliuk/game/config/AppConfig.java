package com.valeriisosliuk.game.config;

import static com.valeriisosliuk.game.model.ActionType.ACTION;
import static com.valeriisosliuk.game.model.ActionType.DEMAND;
import static com.valeriisosliuk.game.model.ActionType.PASS;
import static com.valeriisosliuk.game.model.ActionType.PICK;
import static com.valeriisosliuk.game.model.ActionType.QUIT;
import static com.valeriisosliuk.game.model.ActionType.RESPOND;
import static com.valeriisosliuk.game.model.ActionType.START;
import static com.valeriisosliuk.game.state.State.DEAL_END;
import static com.valeriisosliuk.game.state.State.DEAL_START;
import static com.valeriisosliuk.game.state.State.DEMAND_SUIT;
import static com.valeriisosliuk.game.state.State.GAME_OVER;
import static com.valeriisosliuk.game.state.State.GAME_START;
import static com.valeriisosliuk.game.state.State.INITIAL;
import static com.valeriisosliuk.game.state.State.RESPOND_SUIT;
import static com.valeriisosliuk.game.state.State.TURN_END;
import static com.valeriisosliuk.game.state.State.TURN_IN_PROGRESS;
import static com.valeriisosliuk.game.state.State.TURN_START;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import com.valeriisosliuk.game.model.ActionType;
import com.valeriisosliuk.game.model.Card;
import com.valeriisosliuk.game.model.CardDeck;
import com.valeriisosliuk.game.model.CardHolder;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Player;
import com.valeriisosliuk.game.model.PlayerHolder;
import com.valeriisosliuk.game.observer.ActiveStateObserver;
import com.valeriisosliuk.game.service.GameProvider;
import com.valeriisosliuk.game.service.MessageService;
import com.valeriisosliuk.game.state.ActiveState;
import com.valeriisosliuk.game.state.State;
import com.valeriisosliuk.game.state.actionhandler.ActionHandler;
import com.valeriisosliuk.game.state.initializer.StateInitinalizer;
import com.valeriisosliuk.game.util.Shuffle;

@Configuration
@PropertySource("classpath:game.properties")
public class AppConfig {
    
	@Autowired
	private MessageService messageService;
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
    private StateInitinalizer turnInProgressStateInitializer;
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
    @Resource
    private Observer gameStateObserver;
    @Resource
    private Observer cardHolderObserver;
    @Resource
    private Observer playerHolderObserver;
    @Resource
    private Observer playerObserver;
    
    @Bean(name = "stateInitializers")
    public Map<State, StateInitinalizer> getStateInitializers() {
        Map<State, StateInitinalizer> stateInitializers = new EnumMap<>(State.class);
        stateInitializers.put(INITIAL, initialStateInitializer);
        stateInitializers.put(DEAL_START, dealStartStateInitializer);
        stateInitializers.put(TURN_START, turnStartStateInitializer);
        stateInitializers.put(TURN_IN_PROGRESS, turnInProgressStateInitializer);
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
    	Map<ActionType, ActionHandler> actionHandlers = new EnumMap<>(ActionType.class);
    	actionHandlers.put(START, gameStartActionHandler);
    	actionHandlers.put(PASS, turnEndActionHandler);
    	actionHandlers.put(PICK, pickActionHandler);
    	actionHandlers.put(ACTION, cardMoveActionHandler);
    	actionHandlers.put(DEMAND, demandSuitActionHandler);
    	actionHandlers.put(RESPOND, respondSuitActionHandler);
    	actionHandlers.put(QUIT, quitActionHandler);
    	return actionHandlers;
    }
    
    @Bean(name="player")
    @Scope("prototype")
    public Player getPlayer(String name) {
        Player player = new Player(name) {

            @Override
            protected ActiveState getNewActiveState(String name) {
                return AppConfig.this.getActiveState(name);
            }
            
        };
        player.addObserver(playerObserver);
        return player;
    }
    
    protected ActiveState getActiveState(String name) {
        ActiveState activeState = new ActiveState(name);
        activeState.addObserver(getActiveStateObserver());
        return activeState;
    }

    @Bean(name="playerHolder")
    @Scope("prototype")
    public PlayerHolder getPlayerHolder() {
        PlayerHolder playerHolder = new PlayerHolder() {
            @Override
            protected Player getNewPlayer(String playerName) {
                return AppConfig.this.getPlayer(playerName);
            }
        };
        playerHolder.addObserver(playerHolderObserver);
        return playerHolder;
    }
    
    @Bean(name="game")
    @Scope("prototype")
    public Game getGame() {
        Game game = new Game();
        game.addObserver(gameStateObserver);
        game.addObserver(cardHolderObserver);
        game.setPlayerHolder(getPlayerHolder());
        game.setCardHolder(getCardHolder());
        return game;
    }
    
    @Bean(name="cardHolder")
    @Scope("prototype")
    public CardHolder getCardHolder() {
        CardHolder cardHolder = new CardHolder();
        cardHolder.setCardDeck(getShuffledCardDeck());
        cardHolder.addObserver(cardHolderObserver);
        return cardHolder;
    }
    
    @Bean(name="shuffledCardDeck")
    @Scope("prototype")
    public CardDeck getShuffledCardDeck() {
        List<Card> allCards = Arrays.asList(Card.values());
        return new CardDeck(Shuffle.shuffle(allCards));
    }

    @Bean(name="activeStateObserver")
    @Scope("prototype")
    public ActiveStateObserver getActiveStateObserver() {
        return new ActiveStateObserver();
    }
    
    @Bean(name="gameProvider")
    public GameProvider getGameProvider() {
        return new GameProvider() {
            
            @Override
            public Game getNewGame() {
                return getGame();
            }
        };
    }
}
