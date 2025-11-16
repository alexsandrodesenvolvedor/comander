package com.alexsandro.commander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//ComponentScan(basePackages = {"com.alexsandro.commander.mapper", "com.alexsandro.commander.controller"})
public class ComanderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComanderApplication.class, args);
	}

}
