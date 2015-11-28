package com.valeriisosliuk.game.observer;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

public class CardHolderObserver implements Observer {

	private static final Logger log = Logger.getLogger(CardHolderObserver.class);
	@Override
	public void update(Observable o, Object arg) {
		log.info("Card Holder Updated with argument: " + arg);
		
	}

}
