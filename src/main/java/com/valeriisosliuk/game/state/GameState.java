package com.valeriisosliuk.game.state;

import com.valeriisosliuk.game.observer.AbstractObservable;

public class GameState extends AbstractObservable {
	
	private State state;

	
	public GameState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		if (this.state == state) {
			return;
		}
		this.state = state;
		setChangedAndNotify(state);
	}
}
