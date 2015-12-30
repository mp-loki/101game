package com.valeriisosliuk.dto;

import java.util.Set;

import com.valeriisosliuk.model.Card;

public class ActiveStateDto extends Dto {
    
    private final boolean pickAllowed;
    private final boolean passAllowed;
    private final Set<Card> turnOptions;
    
    public ActiveStateDto(boolean pickAllowed, boolean passAllowed, Set<Card> turnOptions) {
        super(DtoType.ACTIVE);
        this.pickAllowed = pickAllowed;
        this.passAllowed = passAllowed;
        this.turnOptions = turnOptions;
    }
    public boolean isPickAllowed() {
        return pickAllowed;
    }
    public boolean isPassAllowed() {
        return passAllowed;
    }
    public Set<Card> getTurnOptions() {
        return turnOptions;
    }
    @Override
    public String toString() {
        return "ActiveStateDto [pickAllowed=" + pickAllowed + ", passAllowed=" + passAllowed + ", turnOptions=" + turnOptions + "]";
    }
}
