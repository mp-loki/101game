package com.valeriisosliuk.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import com.valeriisosliuk.game.service.UserService;

@Component("customSecurityContextLogoutHandler")
public class CustomSecurityContextLogoutHandler extends SecurityContextLogoutHandler {
    
    private static final Logger log = Logger.getLogger(CustomSecurityContextLogoutHandler.class);
    
    @Autowired
    private UserService userService;
    
    @Override
    public void logout(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response,
            Authentication authentication) {
        super.logout(request, response, authentication);
        String name = authentication.getName();
        log.info("User " + name + " logged off");
        userService.removeUser(name);
    }

}
