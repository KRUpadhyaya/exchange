package com.xyz.exchange.api.service;

import java.util.Comparator;

public class Comparators {
	public static final Comparator<Coin> DESCENDING_COIN_VALUE = (c1, c2) -> {
		return (c1 == c2) ? 0 : ((null == c1) ? -1 : ((null == c2) ? 1 : (c1.value() > c2.value()) ? 1 : -1 ));
	};
	
	public static final Comparator<Coin> ASCENDING_COIN_VALUE =  (c1, c2) -> DESCENDING_COIN_VALUE.compare(c2, c1);
	
	public static final Comparator<Pouch> DESCENDING_POUCH_COIN_VALUE = (p1, p2) ->  (p1 == p2) ? 0 : ((null == p1) ? -1 : ((null == p2) ? 1 : DESCENDING_COIN_VALUE.compare(p1.getType(), p2.getType())));

}
