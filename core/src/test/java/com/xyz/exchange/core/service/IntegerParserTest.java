package com.xyz.exchange.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.xyz.exchange.api.service.ParserException;

@RunWith(SpringRunner.class)
public class IntegerParserTest {
	private IntegerParser parser;

	@Before
	public void setUp() {
		parser = new IntegerParser();
	}

	@Test
	public void whenInputIsEmpty_thenThrowConversionException() {
		try {
			parser.parse("  ");
			fail("Should have thrown ParserException with empty input");
		}
		catch (ParserException ex) {
			//pass			
		}		
	}

	@Test
	public void whenInputIsNull_thenThrowConversionException() {
		try {
			parser.parse(null);
			fail("Should have thrown ParserException with empty null");
		}
		catch (ParserException ex) {
			//pass			
		}		
	}

	@Test
	public void whenInputContainsNonDigits_thenThrowConversionException() {
		try {
			parser.parse("12A35");
			fail("Should have thrown ParserException with non digit characters");
		}
		catch (ParserException ex) {
			//pass			
		}		
	}

	@Test
	public void whenInputIsNegative_thenThrowConversionException() {
		try {
			parser.parse("-1235");
			fail("Should have thrown ParserException with negative number");
		}
		catch (ParserException ex) {
			//pass			
		}		
	}

	@Test
	public void whenInputContainsMinusSign_thenThrowConversionException() {
		try {
			parser.parse("12-35");
			fail("Should have thrown ParserException with minus character");
		}
		catch (ParserException ex) {
			//pass			
		}		
	}

	@Test
	public void whenInputContainsDecimalFraction_thenThrowConversionException() {
		try {
			parser.parse("12.35");
			fail("Should have thrown ParserException with decimal fraction");
		}
		catch (ParserException ex) {
			//pass			
		}		
	}

	@Test
	public void whenInputContainsPeriodButNoDecimalFraction_thenAccept() {
		try {
			Integer value = parser.parse("1235.");
			assertNotNull("Parsed value is null", value);
			assertEquals("Parsed value", 1235, value.doubleValue(), 0);
		}
		catch (ParserException ex) {
			fail("Should have parsed with input \"1235.\"");			
		}		
	}

	@Test
	public void whenInputContainsOnlyDigits_thenAccept() {
		try {
			Integer value = parser.parse("1235");
			assertNotNull("Parsed value is null", value);
			assertEquals("Parsed value", 1235, value.doubleValue(), 0);
		}
		catch (ParserException ex) {
			fail("Should have parsed with input \"1235\"");			
		}		
	}

}
