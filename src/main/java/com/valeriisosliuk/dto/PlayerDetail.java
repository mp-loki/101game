package com.valeriisosliuk.dto;

public class PlayerDetail {

    private String name;
    private int cardCount;

    public PlayerDetail(String name, int cardCount) {
        this.name = name;
        this.cardCount = cardCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

}
