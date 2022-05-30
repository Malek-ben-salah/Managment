package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product addProduct(Product product) {
		System.out.println("adding product: " + product.getProductName());
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		System.out.println("updating product: " + product.getProductName());
		return productRepository.save(product);
	}

	@Override
	public boolean deleteProduct(Long id) {
		System.out.println("deleting product by id : " + id);
		productRepository.deleteById(id);
		return true;
	}

	@Override
	public TablesResponse getAllProducts() {
		System.out.println("getting all products ... ");
		TablesResponse res = new TablesResponse();
		List<String> columnsName = new ArrayList<>();
		res.setTitle("List of Products");
		columnsName.add("product name");
		columnsName.add("price");
		columnsName.add("quantity");
		res.setColmuns(columnsName);
		res.setData(productRepository.findAll());
		return res;
	}

	@Override
	public Product getProductById(Long id) {
		System.out.println("getting product by id: " + id);
		return productRepository.findById(id).get();
	}

	@Override
	public DeleteResponse deleteMultipeProducts(List<Long> ids) {
		System.out.println("delete multipe products : " + ids);
		DeleteResponse deleteResponse = new DeleteResponse();
		try {
			productRepository.deleteAllById(ids);
			deleteResponse.setMessage("delete success");
			deleteResponse.setColor("green");
		} catch (EmptyResultDataAccessException e) {
			System.out.println(e.getMessage());
			deleteResponse.setMessage("delete failed ! ");
			deleteResponse.setColor("red");
		}
		return deleteResponse;
	}

	@Override
	public List<String> searchByProductName(String productName) {
		System.out.println("search by product name: " + productName);
		return productRepository.searchByProductName(productName);
	}

	@Override
	public List<Float> searchByProductPrice(float price) {
		System.out.println("search by product price: " + price);
		return productRepository.searchByProductPrice(price);
	}

	@Override
	public List<Integer> searchByProductQuantity(int quantity) {
		System.out.println("search by product quantity: " + quantity);
		return productRepository.searchByProductQuantity(quantity);
	}

}
