package com.valeriisosliuk.model;

public class GameTurnMessage {
    
    private String player;
    private String message;
    
    public String getPlayer() {
        return player;
    }
    public void setPlayer(String player) {
        this.player = player;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "GameTurnMessage [player=" + player + ", message=" + message + "]";
    }
}

