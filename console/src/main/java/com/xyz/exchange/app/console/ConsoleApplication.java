package com.xyz.exchange.app.console;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(ConsoleApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
