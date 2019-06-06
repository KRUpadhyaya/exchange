package com.xyz.exchange.api.service;

public class ParserException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, Exception cause) {
		super(message, cause);
	}
}
