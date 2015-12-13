package com.valeriisosliuk.game.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    public List<String> getLoggedInUserNames() {
        List<String> userNames = new ArrayList<String>();
        List<Object> userobjects = sessionRegistry.getAllPrincipals();
        for (Object userObject : userobjects) {
            if (userObject instanceof User) {
                User user = (User) userObject;
                userNames.add(user.getUsername());
            }
        }
        return userNames;
    }
    
    public String getCurrentUserName() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername(); //get logged in username
    }

}
