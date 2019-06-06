package com.xyz.exchange.api.service;

public enum Coin {
	CENT(1), NICKEL(5), DIME(10), QUARTER(25), FIFTY(50), THIRTY3(33); //, THIRTY4(34));
	
	private int value;
	
	private Coin(int value){
		this.value = value;
	}
	
	public int value() {
		return value;
	}
	
	public long valueOf(int count) {
		return value * count;
	}
	
	public long countBelow(long amount) {
		return Math.abs(amount) / value;
	}
	
}
