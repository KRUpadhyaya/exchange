package com.xyz.exchange.api.service;

public interface Parser<T extends Number> {
	/**
	 * Returns a description of this parser
	 * @return a {@link String} describing this parser
	 */
	String describe(); 

	/**
	 * Parses the given string as a an instance of {@link T}
	 * @param input a string to parse
	 * @return and instance of {@link T}
	 * @throws ParserException if input can not be parsed
	 */
	T parse(String input) throws ParserException;
}
