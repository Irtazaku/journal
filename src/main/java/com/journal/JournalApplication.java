package com.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class JournalApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}
}
