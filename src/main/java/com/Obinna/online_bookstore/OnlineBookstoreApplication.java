package com.Obinna.online_bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OnlineBookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookstoreApplication.class, args);
	}

}
