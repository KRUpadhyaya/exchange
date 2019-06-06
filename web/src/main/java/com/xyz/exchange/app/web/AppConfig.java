package com.xyz.exchange.app.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xyz.exchange.core.service.ServiceConfig;

@Configuration
@Import(ServiceConfig.class)
public class AppConfig {

}
