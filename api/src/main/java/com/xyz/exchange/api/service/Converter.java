package com.xyz.exchange.api.service;

import java.util.Map;


public interface Converter {
	/**
	 * Splits a dollar amount to an equivalent amount in coins	
	 * @param dollarAmount to be converted
	 * @return count of coins grouped by the type
	 * @throws ConversionException if error in converting
	 */
	Map<Coin, Integer> convert(long dollarAmount, Map<Coin, Integer> context) throws ConversionException;

}
