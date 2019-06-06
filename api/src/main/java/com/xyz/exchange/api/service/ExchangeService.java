package com.xyz.exchange.api.service;

import java.util.List;

public interface ExchangeService {
	/**
	 * Returns a description of the repository
	 * @return a {@link String} describing this repository
	 */
	String describe(); 	
	
	/**
	 * Removes an amount in coins equal to a dollar amount	
	 * @param dollarAmount to be converted
	 * @return Amount in coins equal to the dollar amount. Result is always ordered in descending order of coin values
	 * @throws ConversionException if amount cannot be withdrawn
	 */
	List<Pouch> withdraw(long dollarAmount) throws ConversionException;
	
	/**
	 * Returns a report containing the number of coins grouped by coin types
	 * @return number of coins grouped by coin types. 
	 * @throws ConversionException if error in creating the report
	 */	
	List<Pouch> report() throws ConversionException; 
	
	/**
	 * Returns current balance in dollar amount
	 * @return dollar amount 
	 * @throws ConversionException if error in finding balance
	 * 
	 */
	public long balance()  throws ConversionException;
}
