package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("select P.productName from Product P where P.productName like :name%")
	List<String> searchByProductName(@Param("name") String productName);
	
	@Query("select P.price from Product P where char(P.price) like char(:price)")
	List <Float> searchByProductPrice(@Param("price") float price);
	
	//List<Float> findByPriceEndingWith(float price);
	
	@Query("select P.quantity from Product P where P.quantity like :quantity%")
	List<Integer> searchByProductQuantity(@Param("quantity") int quantity);
	
}
