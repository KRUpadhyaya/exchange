package com.xyz.exchange.api.service;

public class ConversionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConversionException(String message) {
		super(message);
	}
	
	public ConversionException(String message, Exception cause) {
		super(message, cause);
	}

}
