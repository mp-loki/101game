package com.valeriisosliuk.game.observer;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

public class PlayerObserver implements Observer {
	
	private static final Logger log = Logger.getLogger(PlayerObserver.class);
	
	@Override
	public void update(Observable player, Object arg) {
		log.info("Player state changed! " + arg + " " + player);
	}

}
