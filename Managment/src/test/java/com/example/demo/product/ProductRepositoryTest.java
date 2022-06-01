package com.example.demo.product;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@SpringBootTest
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	void canSearchByProductName() {
		// given
		String productName = "asus";
		Product p = new Product(productName, 1550, 19);
		// when
		List<Product> products = productRepository.searchByProductName(productName);
		// then
		assertThat(products).contains(p);
	}

	@Test
	void canSearchByProductPrice() {
		double price = 1550;
		Product p = new Product("asus", price, 19);
		List<Product> prices = productRepository.searchByProductPrice(price);

		assertThat(prices).contains(p);
	}

	@Test
	void canSearchByProductQuantity() {
		int quantity = 10;
		Product p = new Product("asus", 1550, quantity);
		List<Product> quantities = productRepository.searchByProductQuantity(quantity);

		assertThat(quantities).contains(p);
	}

}
