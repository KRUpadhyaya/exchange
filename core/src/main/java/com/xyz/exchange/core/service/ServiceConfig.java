package com.xyz.exchange.core.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xyz.exchange.api.service.Coin;
import com.xyz.exchange.api.service.Converter;
import com.xyz.exchange.api.service.ExchangeService;
import com.xyz.exchange.api.service.Parser;
import com.xyz.exchange.api.service.ParserException;
import com.xyz.exchange.core.db.api.CoinRepository;
import com.xyz.exchange.core.db.impl.CoinRepositoryImpl;

@Configuration
public class ServiceConfig {
	@Autowired
    ApplicationArguments appArgs;
	
//	@Value("${quarters:100}")
//    private int quarters;
//
//	@Value("${nickels:100}")
//    private int nickels;
//
//	@Value("${dimes:100}")
//    private int dimes;
//
//	@Value("${cents:100}")
//    private int cents;
//	
//	@Value("${thirty3s:100}")
//    private int thirty3s;
//	
//	@Value("${thirty4s:100}")
//    private int thirty4s;
//	
//	@Value("${fiftys:100}")
//    private int fiftys;

	
	@Value("${algorithm:minimum}")
    private String algorithm;
	
	private IntegerParser parser = new IntegerParser();
	
	@Bean
	public ExchangeService exchangeService() {
		return new ExchangeServiceImpl();
	}
	
	@Bean
	public CoinRepository coinRepository() {
		CoinRepositoryImpl repository = new CoinRepositoryImpl();
		Stream.of(Coin.values()).forEach(coin -> repository.update(coin, getCount(coin)));
		return repository;
	}
	
	private int getCount(Coin coin) {
		int defaultCount = 100;
		try {
			String key = coin.name().toLowerCase()+"s";
			List<String> values = appArgs.getOptionValues(key);
			return parser.parse(values.get(0));
		}
		catch(ParserException | NullPointerException | java.lang.IndexOutOfBoundsException ignore) {
			return defaultCount;
		}
	}

	
	@Bean
	public Converter converter() {
		switch(algorithm) {
		case "maximum": return Converters.withMaximumCoins();
		default: return Converters.withMinimumCoins();
		}
	}
	
	@Bean
	public Parser<Integer> parser() {
		return parser;
	}
	
	

}
