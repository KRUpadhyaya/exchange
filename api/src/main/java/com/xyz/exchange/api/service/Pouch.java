package com.xyz.exchange.api.service;

import java.util.Collection;
import java.util.stream.Stream;

public class Pouch {
	private Coin type;
	private int count;

	/**
	 * Creates an instance of {@link Pouch} containing an initial number of coins of the specified type
	 * @param count number of coins
	 * @param type of the coins in the pouch
	 * @return an instance of {@link Pouch}
	 */
	public static Pouch with(int count, Coin type) {
		return new Pouch(count, type);
	}

	/**
	 * Returns the sum of amount of cents in every pouch
	 * @param pouches an array of pouches, never null
	 * @return total number of cents in all pouches
	 */
	public static long sum(Pouch...pouches) {
		return sum(Stream.of(pouches));
	}

	private static long sum(Stream<Pouch> pouches) {
		return pouches.mapToLong(Pouch::getAmount).sum();
	}

	/**
	 *  Returns the sum of amount of cents in every pouch
	 * @param pouches a collection of pouches, never null
	 * @return total number of cents in all pouches
	 */
	public static long sum(Collection<Pouch> pouches) {
		return sum(pouches.stream());
	}

	private Pouch(int size, Coin type){
		this.type = type;
		this.count = size;
	}

	public Coin getType() {
		return type;
	}

	public long getAmount() {	
		return type.valueOf(count);
	}

	public int getCount() {
		return count;
	}
	
	/**
	 * Removes given number of coins if available 
	 * @param count the number of coins to be removed;
	 * @return the number of coins removed
	 */
	public int remove(int count) {
		int removed = Math.min(this.count,  count);
		this.count-= removed;
		return removed;
	}	
	
	public int available(long amount) {
		if(0 == amount || 0 == count) {
			return 0;
		}
		if(amount < getType().value()) {
			return 0;
		}
		
		long count = getType().countBelow(amount);		
		return (int)Math.min(count, this.count);  
	}
	
	public Pouch clone() {
		return Pouch.with(getCount(), getType());
	}
	
	public boolean isNotEmpty() {
		return (count > 0);
	}
}
