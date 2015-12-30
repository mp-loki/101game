package com.valeriisosliuk.game.service;

/**
 * 
 * @author valerii.sosliuk
 *
 */
public class MessageServiceLocator {

    private static MessageService messageService;

    public static MessageService getMessageService() {
        return messageService;
    }

    static void setMessageService(MessageService messageService) {
        MessageServiceLocator.messageService = messageService;
    }
    
    
}
