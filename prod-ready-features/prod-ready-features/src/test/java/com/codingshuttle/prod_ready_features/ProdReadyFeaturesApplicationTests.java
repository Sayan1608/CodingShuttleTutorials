package com.codingshuttle.prod_ready_features;

import com.codingshuttle.prod_ready_features.clients.CurrencyRestClient;
import com.codingshuttle.prod_ready_features.clients.EmployeeRestClient;
import com.codingshuttle.prod_ready_features.dtos.EmployeeDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdReadyFeaturesApplicationTests {

	@Autowired
	EmployeeRestClient employeeRestClient;

	@Autowired
	CurrencyRestClient currencyRestClient;

	@Test
	@Order(3)
	void getAllEmployess(){
		System.out.println(employeeRestClient.getAllEmployees());
	}

	@Test
	@Order(2)
	void getEmployeeById(){
		System.out.println(employeeRestClient.getEmployeeById(1L));
	}

	@Test
	@Order(1)
	void createNewEmployee(){
		EmployeeDTO employeeDTO = EmployeeDTO
				.builder()
				.id(null)
				.age(2)
				.name("Priyanka")
				.email("priyanka@coding-shuttle.com")
				.dateOfJoining(LocalDate.of(2024, 1, 1))
				.isActive(true)
				.salary(BigDecimal.valueOf(25000.00))
				.primeNumber(11)
				.build();

		EmployeeDTO savedEmployeeDto = employeeRestClient.createNewEmployee(employeeDTO);
		System.out.println(savedEmployeeDto);
	}

	@Test
	void convertCurrency(){
		System.out.println(currencyRestClient
				.convertCurrency("INR","USD,EUR"));
	}

}
