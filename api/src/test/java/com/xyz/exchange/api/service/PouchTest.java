package com.xyz.exchange.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PouchTest {
	
	@Test
	public void whenCreated_EveryFieldMatches() {
		Pouch pouch = Pouch.with(100, Coin.NICKEL);
		assertNotNull("Pouch is null", pouch);
		assertEquals("Pouch coin count", 100, pouch.getCount());
		assertEquals("Pouch coin amount", 500, pouch.getAmount());
		assertEquals("Pouch coin type", Coin.NICKEL, pouch.getType());
	}
	
	@Test
	public void givenPouch_whenAmountIsMoreThanAvailable_ReturnAll() {
		Pouch pouch = Pouch.with(100, Coin.DIME);
		assertEquals("Pouch coin amount", 100, pouch.available(1500));
	}

	@Test
	public void givenPouch_whenAmountIsLessThanAvailable_ReturnRequested() {
		Pouch pouch = Pouch.with(100, Coin.QUARTER);
		assertEquals("Pouch coin amount", 60, pouch.available(1500));
	}

	@Test
	public void givenPouch_whenAmountIsEqualToAvailable_ReturnAll() {
		Pouch pouch = Pouch.with(100, Coin.CENT);
		assertEquals("Pouch coin amount", 100, pouch.available(100));
	}
	
	@Test
	public void givenPouch_whenRemovedAmountIsEqualToAvailable_AmountIsZero() {
		Pouch pouch = Pouch.with(100, Coin.CENT);
		pouch.remove(100);
		assertEquals("Pouch coin amount", 0, pouch.getAmount());
	}

	@Test
	public void givenPouch_whenRemovedAmountIsLessThanAvailable_AmountIsNotZero() {
		Pouch pouch = Pouch.with(100, Coin.CENT);
		pouch.remove(10);
		assertEquals("Pouch coin amount", 90, pouch.getAmount());
	}

}
