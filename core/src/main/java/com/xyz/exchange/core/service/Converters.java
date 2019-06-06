package com.xyz.exchange.core.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.xyz.exchange.api.service.Coin;
import com.xyz.exchange.api.service.Comparators;
import com.xyz.exchange.api.service.Converter;

public class Converters  {
	
	static Converter withMaximumCoins() {
		return new ConverterImpl(Comparators.ASCENDING_COIN_VALUE);
	}

	static Converter withMinimumCoins() {
		return new ConverterImpl(Comparators.ASCENDING_COIN_VALUE);
	}

	
	private static class ConverterImpl extends OrderedConversion {
		
		private Comparator<Coin> comparator; 

		private ConverterImpl(Comparator<Coin> comparator){
			this.comparator = comparator;
		}
		
		@Override
		protected List<Coin> orderCoins(Map<Coin, Integer> context){
			return context.keySet().stream().sorted(comparator).collect(Collectors.toList());
		}		
	}
}