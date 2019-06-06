package com.xyz.exchange.api.service;

public class InsufficientFundsException extends ConversionException {
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String message) {
		super(message);
	}
}
