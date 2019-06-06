package com.xyz.exchange.core.service;

import org.springframework.stereotype.Service;

import com.xyz.exchange.api.service.Parser;
import com.xyz.exchange.api.service.ParserException;

@Service
public class IntegerParser implements Parser<Integer> {

	@Override
	public String describe() {
		return "Parser of string as a integer value";
	}

	@Override
	public Integer parse(String input) throws ParserException{
		if(null == input) {
			throw new ParserException("Invalid input: null");
		}
		String temp = input.trim();
		if(temp.isEmpty()){
			throw new ParserException(String.format("Invalid input: \"%1$s\"", input));
		}
		if(temp.contains("-")) {
			throw new ParserException(String.format("Invalid input with minus character: \"%1$s\"", input));
		}
		if(temp.contains(".")) {
			if(!temp.endsWith(".")) {
				throw new ParserException(String.format("Invalid input with period : \"%1$s\"", input));
			}
			temp = temp.substring(0, temp.length()-1);
		}
		try {
			return Integer.valueOf(temp);
		}
		catch (NumberFormatException ex) {
			throw new ParserException(String.format("Invalid input: \"%1$s\"", input), ex);			
		}
	}
}
