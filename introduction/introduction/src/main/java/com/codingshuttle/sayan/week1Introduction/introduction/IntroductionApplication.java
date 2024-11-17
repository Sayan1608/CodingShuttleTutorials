package com.codingshuttle.sayan.week1Introduction.introduction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntroductionApplication implements CommandLineRunner {
//	@Autowired
//	Apple obj1;
//	@Autowired
//	Apple obj2;

	final private DbService dbService;

	public IntroductionApplication(DbService dbService) {
		this.dbService = dbService;
	}

	public static void main(String[] args) {

		SpringApplication.run(IntroductionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		obj1.eatApple();
//		obj2.eatApple();
//		System.out.println(obj1.hashCode());
//		System.out.println(obj2.hashCode());
		dbService.getData();
	}
}
