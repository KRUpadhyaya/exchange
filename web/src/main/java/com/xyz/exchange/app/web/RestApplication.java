package com.xyz.exchange.app.web;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(RestApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
