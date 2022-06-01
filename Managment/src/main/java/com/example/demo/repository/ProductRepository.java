package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("select P from Product P where P.productName like :name%")
	List<Product> searchByProductName(@Param("name") String productName);

	@Query("select P from Product P where cast(P.price as string) like cast(:price as int) || '%' ")
	List<Product> searchByProductPrice(@Param("price") double price);

	@Query("select P from Product P where cast(P.quantity as string) like :quantity || '%' ")
	List<Product> searchByProductQuantity(@Param("quantity") int quantity);

}
