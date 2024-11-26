package com.codingshuttle.jpa;

import com.codingshuttle.jpa.entities.ProductEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JpaTutorialApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	@Order(1)
	void contextLoads() {
	}

	@Test
	@Order(2)
	void testRepository(){
		ProductEntity productEntity = ProductEntity
				.builder()
				.sku("HUL001")
				.price(BigDecimal.valueOf(300.05))
				.title("Nivea Soft")
				.quantity(15L)
				.build();

		productRepository.save(productEntity);
	}

	@Test
	@Order(3)
	void testRepoUtilityMethods(){
		List<ProductEntity> allProducts = productRepository.findAll();
		System.out.println(allProducts);
	}

	@Test
	@Order(4)
	void testCustomQueryMethods(){
		List<ProductEntity> productByName = productRepository.findByTitleIsLike("%Britannia%");
		System.out.println("ProductByName => " + productByName);

		System.out.println(productRepository.existsByTitle("Britannia Rusk1"));

		System.out.println("Created on After : 2024-11-24 :: " + productRepository.findByCreatedOnBetween(
				LocalDateTime.of(2024,11,24,0,0,0),
				LocalDateTime.of(2024,11,27,0,0,0),null));

		System.out.println(productRepository.findByTitleAndPrice("Mirinda", "50.5"));
	}

}
