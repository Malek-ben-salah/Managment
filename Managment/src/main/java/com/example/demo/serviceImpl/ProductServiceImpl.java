package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
	public TablesResponse getAllProducts(int page) {
		System.out.println("getting all products ... ");
		TablesResponse res = new TablesResponse();
		List<String> columnsName = new ArrayList<>();
		res.setTitle("List of Products");
		columnsName.add("product name");
		columnsName.add("price");
		columnsName.add("quantity");
		res.setColmuns(columnsName);
		res.setData(productRepository.findAll(PageRequest.of(0, page)).toList());
		return res;
	}

	@Override
	public Product getProductById(Long id) {
		System.out.println("getting product by id: " + id);
		return productRepository.findById(id).get();
	}

	@Override
	public List<DeleteResponse> deleteMultipeProducts(List<Long> ids) {
		System.out.println("delete multipe products : " + ids);
		List<DeleteResponse> deleteResponses = new ArrayList<DeleteResponse>();
		List<Long> inexistantIds = new ArrayList<Long>();
		List<Product> products = productRepository.findAllById(ids);
		List<Long> existantId = new ArrayList<Long>();
		products.forEach(prod -> {
			existantId.add(prod.getId());
		});
		try {
			if (ids == null || ids.isEmpty()) {
				deleteResponses.add(new DeleteResponse("delete failed ! ", "error"));
				System.out.println("array of ids is null or empty ! ");
			}
		} catch (HttpMessageNotReadableException e) {
			System.out.println(e.getMessage());
		}

		if (products.size() < ids.size()) {
			for (Long id : ids) {
				if (!existantId.contains(id)) {
					inexistantIds.add(id);
				}
			}

			deleteResponses.add(new DeleteResponse("delete failed for those users ids: " + inexistantIds, "error"));
		}
		if (!products.isEmpty()) {
			deleteResponses.add(new DeleteResponse("delete success", "success"));
			System.out.println(productRepository.findAllById(ids));
			productRepository.deleteAllInBatch(products);
		}
		return deleteResponses;

	}

	@Override
	public List<String> searchByProductName(String productName) {
		System.out.println("search by product name: " + productName);
		return productRepository.searchByProductName(productName);
	}

	@Override
	public List<Double> searchByProductPrice(double price) {
		System.out.println("search by product price: " + price);
		return productRepository.searchByProductPrice(price);
	}

	@Override
	public List<Integer> searchByProductQuantity(int quantity) {
		System.out.println("search by product quantity: " + quantity);
		return productRepository.searchByProductQuantity(quantity);
	}

}
