package com.xyz.exchange.core.db.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.xyz.exchange.api.service.Coin;
import com.xyz.exchange.core.db.api.CoinRepository;

public class CoinRepositoryImpl implements CoinRepository {
	private Map<Coin, Integer> data = new HashMap<>();

	@Override
	public int delete(Coin type, int count) {
		int removed = 0;
		Integer current = data.remove(type);
		if(null != current) {
			removed = Math.min(current, count);
			current-=removed;
			if(0 != current) {
				data.put(type, current);
			}

		}
		return removed;
	}

	@Override
	public int update(Coin type, int count) {
		if(count < 0) {
			throw new IllegalArgumentException(String.format("Setting number of %1$ss to negative value of %2%d is not allowed", type, count));
		}
		if(0 == count) {
			return (null == data.remove(type)) ? 0 : 1;
		}
		data.put(type, count);
		return 1;
	}

	@Override
	public Map<Coin, Integer> report() {
		return Collections.unmodifiableMap(data);
	}
}
