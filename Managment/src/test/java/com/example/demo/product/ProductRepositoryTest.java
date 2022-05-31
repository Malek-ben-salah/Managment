package com.example.demo.product;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repository.ProductRepository;

@SpringBootTest
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	void canSearchByProductName() {
		// given
		String productName = "asus";
		// when
		List<String> names = productRepository.searchByProductName(productName);
		// then
		assertThat(names).contains(productName);
	}

	@Test
	void canSearchByProductPrice() {
		double price = 1550;

		List<Double> prices = productRepository.searchByProductPrice(price);

		assertThat(prices).contains(price);
	}

	@Test
	void canSearchByProductQuantity() {
		int quantity = 10;

		List<Integer> quantities = productRepository.searchByProductQuantity(quantity);

		assertThat(quantities).contains(quantity);
	}

}
