package com.codingshuttle.prod_ready_features;

import com.codingshuttle.prod_ready_features.clients.EmployeeRestClient;
import com.codingshuttle.prod_ready_features.dtos.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProdReadyFeaturesApplicationTests {

	@Autowired
	EmployeeRestClient employeeRestClient;

	@Test
	void getAllEmployess(){
		System.out.println(employeeRestClient.getEmployeeDetails());
	}

}
