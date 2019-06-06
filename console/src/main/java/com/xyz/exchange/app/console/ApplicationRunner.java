package com.xyz.exchange.app.console;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.xyz.exchange.api.service.ExchangeService;
import com.xyz.exchange.api.service.ConversionException;
import com.xyz.exchange.api.service.Parser;
import com.xyz.exchange.api.service.ParserException;
import com.xyz.exchange.api.service.Pouch;

@Component
public class ApplicationRunner implements CommandLineRunner{
	@Autowired
	private ExchangeService coinService;

	@Autowired
	private Parser<Integer> inputParser;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(String.format("%1$s started", coinService.describe()));
		System.out.println("Available coins");
		show(coinService.report());
		convert();
		System.out.println(String.format("%1$s ended", coinService.describe()));
	}

	private void convert() throws Exception {		
		try(Scanner in = new Scanner(System.in)) {
			while(true) {
				System.out.print("Enter positive full dollar amount: ");
				String input = in. nextLine();
				if("exit".equalsIgnoreCase(input)) {
					System.out.println("User selected to exit");
					break;
				}			
				try {
					int dollarAmount = inputParser.parse(input);			
					List<Pouch> pouches = coinService.withdraw(dollarAmount);
					show(pouches);
					if(0 == coinService.balance() ) {
						System.out.println("No more coins available");
						break;
					}				
				}
				catch(ParserException exception) {
					System.err.println(exception.getMessage());
				}
				catch(ConversionException exception) {
					System.err.println(exception.getMessage());
				}			
			}
		}
		finally {
			// nothing to do
		}
	}

	private void show(List<Pouch> pouchList) {
		System.out.println("=============================================");
		System.out.printf("%1$13s %2$5s  %3$15s\n", "Denomination", "Count", "Amount");
		System.out.println("=============================================");
		pouchList.forEach(pouch -> System.out.printf("%1$13s %2$5d  %3$.2f\n", pouch.getType(), pouch.getCount(), pouch.getAmount()/100.0));
		System.out.println("=============================================");
		System.out.printf("%1$13s %2$5s  %3$.2f\n", "Total", "", Pouch.sum(pouchList)/100.0);		
	}
}
