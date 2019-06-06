package com.xyz.exchange.app.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.exchange.api.service.ExchangeService;
import com.xyz.exchange.api.service.ConversionException;
import com.xyz.exchange.api.service.Pouch;

@RestController
public class ExchangeController {
	@Autowired
	private ExchangeService coinService;

	@RequestMapping(value = "/exchange/coins", method = RequestMethod.GET)
    public ResponseEntity<List<Pouch>> findCoins(@RequestParam("amount") int dollarAmount) {
		try {
			List<Pouch> pouches = coinService.withdraw(dollarAmount);
			return new ResponseEntity<List<Pouch>>(pouches, HttpStatus.OK);
        }
		catch(ConversionException ex) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}        
    }
	
	@RequestMapping(value = "/exchange", method = RequestMethod.GET)
    public ResponseEntity<List<Pouch>> findAvailable() {
		try {
			List<Pouch> pouches = coinService.report();
			return new ResponseEntity<List<Pouch>>(pouches, HttpStatus.OK);
        }
		catch(ConversionException ex) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}        
    }

 }
