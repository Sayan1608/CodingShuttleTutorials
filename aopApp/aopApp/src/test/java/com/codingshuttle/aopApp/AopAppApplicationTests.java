package com.codingshuttle.aopApp;

import com.codingshuttle.aopApp.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AopAppApplicationTests {
	@Autowired
	private ShipmentService shipmentService;

	@Test
	void testOrderPackage() {
		String returnVal = shipmentService.orderPackage(5L);
		log.info("return value {}",returnVal);
	}

	@Test
	void testTrackPackage() {
		shipmentService.trackPackage(101L);
	}

}
