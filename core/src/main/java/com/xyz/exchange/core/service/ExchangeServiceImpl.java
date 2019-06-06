package com.xyz.exchange.core.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.xyz.exchange.api.service.Coin;
import com.xyz.exchange.api.service.ExchangeService;
import com.xyz.exchange.api.service.ConversionException;
import com.xyz.exchange.api.service.Converter;
import com.xyz.exchange.api.service.InsufficientFundsException;
import com.xyz.exchange.api.service.Pouch;
import com.xyz.exchange.core.db.api.CoinRepository;

public class ExchangeServiceImpl implements ExchangeService {
	private static final long centsPerDollar = 100;

	@Autowired
	private Converter converter;

	@Autowired
	private CoinRepository repository;


	@Override
	public String describe() {
		return "Default Coin Service";
	}

	@Override
	public List<Pouch> withdraw(long dollarAmount) throws ConversionException {
		if(1 > dollarAmount) {
			throw new IllegalArgumentException("Dollar amount must be positive: " + dollarAmount);
		}

		long requestedCents = dollarAmount * centsPerDollar;
		long availableCents =  balance();
		if(requestedCents > availableCents) {
			throw new InsufficientFundsException("Insufficient funds. Maximum available is: " +  availableCents / 100);
		}
		try {
			Map<Coin, Integer> context = repository.report();		
			Map<Coin, Integer> converted = converter.convert(dollarAmount, context);
			if(converted.isEmpty()) {
				throw new ConversionException("Failed to convert dollar to coins");
			}
			for(Map.Entry<Coin, Integer> entry : converted.entrySet()) {
				repository.delete(entry.getKey(), entry.getValue());
			}
			return toPouchList(converted);
		}
		catch (SQLException ex) {
			throw new ConversionException("Failed to convert dollar to coins", ex);
		}
	}
	
	private List<Pouch> toPouchList(Map<Coin, Integer> coinCountMap){
		List<Pouch> result = coinCountMap.entrySet().parallelStream().map(entry -> Pouch.with(entry.getValue(), entry.getKey())).collect(Collectors.toList());
		if(result.size() > 1) {
			result.sort((p1, p2) -> p2.getType().value() - p1.getType().value());
		}
		return result;
	}


	@Override
	public List<Pouch> report() throws ConversionException {
		try {
			return toPouchList(repository.report());
		}
		catch (SQLException ex) {
			throw new ConversionException("Failed to report on the repository", ex);
		}
	}

	@Override
	public long balance() throws ConversionException {
		try {
			return repository.report().entrySet().stream().mapToLong(entry -> entry.getKey().valueOf(entry.getValue())).sum();
		}
		catch (SQLException ex) {
			throw new ConversionException("Failed to report on the repository", ex);
		}
	}
}
