package com.example.demo.service;

import java.util.List;

import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.Product;

public interface ProductService {

	// add product
	Product addProduct(Product product);

	// update product
	Product updateProduct(Product product);

	// delete product by id
	boolean deleteProduct(Long id);

	// get all the products
	TablesResponse getAllProducts();

	// get product by id
	Product getProductById(Long id);

	// Delete multipe products
	DeleteResponse deleteMultipeProducts(List<Long> ids);

	// search by product name
	List<String> searchByProductName(String productName);

	// search by product price
	List<Float> searchByProductPrice(float price);

	// search by product quantity
	List<Integer> searchByProductQuantity(int quantity);
}