package com.valeriisosliuk.game.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.service.UserService;

@Component
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {
    
    private static final Logger log = Logger.getLogger(AuthenticationListener.class);
    
    @Autowired
    private UserService userService;
    
    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AuthenticationSuccessEvent) {
            String username = event.getAuthentication().getName();
            log.info("User " + username + " successfully logged in");
            userService.addUser(username);
        }
    }
}
