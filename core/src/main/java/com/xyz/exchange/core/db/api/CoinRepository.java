package com.xyz.exchange.core.db.api;

import java.sql.SQLException;
import java.util.Map;

import com.xyz.exchange.api.service.Coin;

public interface CoinRepository {
	/**
	 * Removes coins from the repository	
	 * @param type of the coin to be removed
	 * @param count number of the coins to be removed
	 * @return Number of coins removed
	 * @throws SQLException if failed to removed
	 */
	int delete(Coin type, int count) throws SQLException;
	
	/**
	 * Sets the number of coins in the the repository	
	 * @param type of the coin to be updated
	 * @param count number of the coin types to be updated
	 * @return Number of coins updated. Alwasys 1 
	 * @throws SQLException if failed to update
	 */
	int update(Coin type, int count) throws SQLException;
	
	/**
	 * Returns a report containing the number of coins grouped by coin types
	 * @return number of coins grouped by coin types. 
	 * @throws SQLException if error in creating the report
	 */	
	Map<Coin, Integer> report() throws SQLException; 


}
