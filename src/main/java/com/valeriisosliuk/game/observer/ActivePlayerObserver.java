package com.valeriisosliuk.game.observer;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

public class ActivePlayerObserver implements Observer {

	private static final Logger log = Logger.getLogger(ActivePlayerObserver.class);
	
	@Override
	public void update(Observable o, Object arg) {
		log.info("Updated object: " + o + "with argument: " + arg);
	}

}
