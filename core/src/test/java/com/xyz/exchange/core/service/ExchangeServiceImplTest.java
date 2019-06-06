package com.xyz.exchange.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import com.xyz.exchange.api.service.Coin;
import com.xyz.exchange.api.service.ConversionException;
import com.xyz.exchange.api.service.Converter;
import com.xyz.exchange.api.service.InsufficientFundsException;
import com.xyz.exchange.api.service.Pouch;
import com.xyz.exchange.core.db.api.CoinRepository;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeServiceImplTest {
	
	@Mock 
	CoinRepository repositoryMock;
	
	@Mock
	Converter converterMock;
	
	private Converter converter = Converters.withMaximumCoins();
	
	
	@InjectMocks
	ExchangeServiceImpl service;	
	
	Map<Coin, Integer> data;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		data = Stream.of(Coin.values()).collect(Collectors.toMap(coin -> coin, coin -> 100));
		when(repositoryMock.report()).thenReturn(Collections.unmodifiableMap(data));
		when(repositoryMock.delete(any(Coin.class), anyInt())).then(invocation -> delete(invocation));
		when(converterMock.convert(anyLong(), any(Map.class))).then(invocation -> convert(invocation));
		
	}
	
	private int delete(InvocationOnMock invocation) {
		Coin coin = invocation.getArgument(0);
		int count = invocation.getArgument(1);
		int current = data.get(coin);
		data.put(coin,  current - count);
		return count;
	}
	
	private Map<Coin, Integer> convert(InvocationOnMock invocation) throws ConversionException {
		long dollarAmount = invocation.getArgument(0);
		Map<Coin, Integer> context = invocation.getArgument(1);
		return converter.convert(dollarAmount, context);
	}

	@Test
	public void whenAmountIsMoreThanAvailable_thenShouldGetInsufficientFunds() throws Exception {
		doTestInsufficientFunds(0, 200);
	}

	@Test
	public void whenAmountIs20AndThen22_thenShouldGetInsufficientFunds() throws Exception {
		doTestInsufficientFunds(103, 22);
	}

	private void doTestInsufficientFunds(long first, long second) throws Exception {
		try {
			if( first > 0) {
				service.withdraw(first);
			}
			service.withdraw(second);
			fail("Should have failed due to insufficient funds");
		}
		catch(InsufficientFundsException ex) {
			// test passes
		}		
	}

	@Test
	public void whenAmountIsNegative_thenShouldGetIllegalArgumentException() throws Exception {
		try {
			service.withdraw(-1);
			fail("Should have failed due to illegal argument exception");
		}
		catch(IllegalArgumentException ex) {
			// test passes
		}		
	}
	
	@Test
	public void whenAmountIsLessThanAvailable_thenShouldGetCoins() throws Exception {
		try {
			List<Pouch> pouches = service.withdraw(10);
			assertNotNull("Conversion returned null pouch list", pouches);
			assertFalse("Conversion returned empty pouch list", pouches.isEmpty());	
			assertEquals("Converted amount", 10.0, Pouch.sum(pouches)/100, 0.0);
		}
		catch(ConversionException ex) {
			fail("Failed to convert: " + ex.getMessage());
		}		
	}
	
	@Test
	public void whenAmountIs25_thenShouldGet100Quarters() throws Exception {
		doTestRemove(83, 25, Pouch.with(100, Coin.QUARTER));
	}

	@Test
	public void whenAmountIs24And2_thenShouldGet4QuartersAnd10Dimes() throws Exception {
		doTestRemove(107, 2, Pouch.with(4, Coin.QUARTER), Pouch.with(10, Coin.DIME));
	}

	@Test
	public void whenAmountIs40And1_thenShouldGet100Cents() throws Exception {
		doTestRemove(123, 1, Pouch.with(100, Coin.CENT));
	}

	private void doTestRemove(long first, long second, Pouch... expected) throws Exception {
		try {
			if(first > 0) {
				service.withdraw(first);
			}
			List<Pouch> actual = service.withdraw(second);			
			assertNotNull("Conversion returned null pouch list", actual);
			assertEquals("Expected pouch count", expected.length, actual.size());
			for(int i = 0; i < expected.length; i++) {
				assertEquals(String.format("Pouch#%1$d type ", i), expected[i].getType(), actual.get(i).getType());
				assertEquals(String.format("Pouch#%1$d count ", i), expected[i].getCount(), actual.get(i).getCount());
			}
		}
		catch(ConversionException ex) {
			fail("Failed to convert: " + ex.getMessage());
		}		
	}
	
	@Test
	public void whenAmountIs41_thenShouldGet100OfEach() throws Exception {
		doTestRemove(0, 124, Pouch.with(100, Coin.FIFTY), Pouch.with(100, Coin.THIRTY3), Pouch.with(100, Coin.QUARTER), Pouch.with(100, Coin.DIME), Pouch.with(100, Coin.NICKEL), Pouch.with(100, Coin.CENT));
	}

	public void whenAmountIs40_thenShouldNotBeMore() throws Exception  {
		try {
			service.withdraw(40);
			assertTrue("Converter has more", service.balance() > 0);
		}
		catch(ConversionException ex) {
			fail("Failed to convert: " + ex.getMessage());
		}		
	}
	
	public void whenAmountIs41_thenShouldNotBeMore() throws Exception  {
		try {
			service.withdraw(41);
			assertFalse("Converter has more", service.balance() > 0);
		}
		catch(ConversionException ex) {
			fail("Failed to convert: " + ex.getMessage());
		}		
	}

}
