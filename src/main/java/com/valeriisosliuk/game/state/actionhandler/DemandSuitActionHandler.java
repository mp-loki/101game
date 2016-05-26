package com.valeriisosliuk.game.state.actionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.Action;
import com.valeriisosliuk.game.dto.InfoDto;
import com.valeriisosliuk.game.model.Game;
import com.valeriisosliuk.game.model.Suit;
import com.valeriisosliuk.game.service.MessageServiceImpl;
import com.valeriisosliuk.game.state.State;

@Component("demandSuitActionHandler")
public class DemandSuitActionHandler implements ActionHandler {

    @Autowired
    private MessageServiceImpl messageService;
    
    @Override
    public State handleAction(Game game, Action action) {
        Suit demandedSuit = action.getDemandedSuit();
        game.getPlayerHolder().getNextActivePlayer().getActiveState().setDemandedSuit(demandedSuit);
        String message = game.getActivePlayer().getName() + " demands " + demandedSuit;
        messageService.sendToAll(game.getPlayerNames(), new InfoDto(message));
        return State.RESPOND_SUIT;
    }

}
