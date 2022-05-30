package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		return ResponseEntity.ok().body(productService.addProduct(product));
	}

	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		return ResponseEntity.ok().body(productService.updateProduct(product));
	}

	@GetMapping(value = "/page/{page}")
	public ResponseEntity<TablesResponse> getAllProducts(@PathVariable int page) {
		return ResponseEntity.ok().body(productService.getAllProducts(page));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productService.getProductById(id));
	}

	@DeleteMapping(value = "/{id}")
	public boolean deleteProductById(@PathVariable Long id) {
		return productService.deleteProduct(id);
	}

	@GetMapping(value = "/searchname/{name}")
	public ResponseEntity<List<String>> searchByProductNames(@PathVariable String name) {
		return ResponseEntity.ok().body(productService.searchByProductName(name));
	}

	@GetMapping(value = "/searchprice/{price}")
	public ResponseEntity<List<Double>> searchByProductPrice(@PathVariable double price) {
		return ResponseEntity.ok().body(productService.searchByProductPrice(price));
	}

	@GetMapping(value = "/searchquantity/{quantity}")
	public ResponseEntity<List<Integer>> searchByProductQuantity(@PathVariable int quantity) {
		return ResponseEntity.ok().body(productService.searchByProductQuantity(quantity));
	}

	@DeleteMapping
	public ResponseEntity<List<DeleteResponse>> deleteMultipeProducts(@RequestBody List<Long> ids) {
		return ResponseEntity.ok().body(productService.deleteMultipeProducts(ids));
	}
}
