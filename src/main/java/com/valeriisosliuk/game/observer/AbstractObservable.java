package com.valeriisosliuk.game.observer;

import java.util.Observable;

public class AbstractObservable extends Observable{
	
	protected <T> void setChangedAndNotify(T t) {
		setChanged();
		notifyObservers(t);
	}
	
	protected <T> void setChangedAndNotify() {
	    setChanged();
	    notifyObservers();
	}
}
