package com.valeriisosliuk.game.observer;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.dto.UsersUpdateDto;
import com.valeriisosliuk.game.service.MessageService;
import com.valeriisosliuk.game.service.UserHolder;

@Component
public class UserHolderObserver implements Observer {
    private static final Logger log = Logger.getLogger(UserHolderObserver.class);
    
    @Autowired
    private MessageService messageService;
    
    @Override
    public void update(Observable observable, Object arg) {
        UserHolder userHolder = (UserHolder) observable;  
        List<String> pendingUsers = userHolder.getAvailableUsers();
        messageService.sendToAll(pendingUsers, new UsersUpdateDto(userHolder.getLoggedInUsers()));
        log.info("User holder state changed: " + userHolder.getLoggedInUsers());
    }
}
