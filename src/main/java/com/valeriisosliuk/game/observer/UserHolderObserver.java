package com.valeriisosliuk.game.observer;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import com.valeriisosliuk.game.service.UserHolder;

public class UserHolderObserver implements Observer {
    private static final Logger log = Logger.getLogger(UserHolderObserver.class);

    @Override
    public void update(Observable observable, Object arg) {
        UserHolder userHolder = (UserHolder) observable;
        log.info("User holder state changed: " + userHolder.getLoggedInUsers());
    }
}
