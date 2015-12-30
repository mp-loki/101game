package com.valeriisosliuk.game.service;

/**
 * 
 * @author valerii.sosliuk
 *
 */
public class ServiceLocator {

    private static MessageService messageService;
    private static GameService gameService;

    public static MessageService getMessageService() {
        return messageService;
    }

    static void setMessageService(MessageService messageService) {
        ServiceLocator.messageService = messageService;
    }

    public static GameService getGameService() {
        return gameService;
    }

    static void setGameService(GameService gameService) {
        ServiceLocator.gameService = gameService;
    }
}
