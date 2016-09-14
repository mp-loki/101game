package com.valeriisosliuk.game.service;

public class WrongGameInstancePickedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5032113757728306479L;

	public WrongGameInstancePickedException() {
	}

	public WrongGameInstancePickedException(String message) {
		super(message);
	}

	public WrongGameInstancePickedException(Throwable cause) {
		super(cause);
	}

	public WrongGameInstancePickedException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongGameInstancePickedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
