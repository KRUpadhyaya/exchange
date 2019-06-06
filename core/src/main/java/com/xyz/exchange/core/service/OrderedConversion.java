package com.xyz.exchange.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xyz.exchange.api.service.Coin;
import com.xyz.exchange.api.service.ConversionException;
import com.xyz.exchange.api.service.Converter;

public abstract class OrderedConversion  implements Converter {	
	
	@Override
	public Map<Coin, Integer> convert(long dollarAmount, Map<Coin, Integer> context) throws ConversionException {
		Map<Coin, Integer> result = new HashMap<>();
		long remaining = Math.abs(dollarAmount) * 100;
		
		if(context.isEmpty() || 0 == remaining) {
			return result;
		}
		
		List<Coin> coins = orderCoins(context);
		for(Coin coin : coins) {
			if(0 == remaining) {
				break;
			}
			Integer available =  context.get(coin);
			if((null == available) || 0 == available) {
				continue;
			}
			long needed = coin.countBelow(remaining);
			int count = (needed > available)? available : (int)needed ;//safe to cast
			result.put(coin,  count);
			remaining-=coin.valueOf(count);
		}
		if(remaining > 0) {
			result.clear();
		}			
		return result;			
	}
	
	protected abstract List<Coin> orderCoins(Map<Coin, Integer> context);
}