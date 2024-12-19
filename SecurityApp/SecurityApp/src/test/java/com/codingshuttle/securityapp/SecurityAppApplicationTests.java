package com.codingshuttle.securityapp;

import com.codingshuttle.securityapp.entities.User;
import com.codingshuttle.securityapp.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityAppApplicationTests {
	@Autowired
	private JwtService jwtService;

	@Test
	void generateJwtToken(){
		User user = new User(10L,"sayan@gmail.com","1234");
		String token = jwtService.generateToken(user);
		System.out.println(token);
		System.out.println("User Id : " + jwtService.getUserIdFromToken(token));
	}

}
