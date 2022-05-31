package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.Product;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;

public interface ProductService {

	// add product
	Product addProduct(Product product);

	// update product
	Product updateProduct(Product product);

	// delete product by id
	boolean deleteProduct(Long id);

	// get all the products
	TablesResponse getAllProducts(int page);

	// get product by id
	Product getProductById(Long id);

	// Delete multipe products
	List<DeleteResponse> deleteMultipeProducts(List<Long> ids);

	// search by product name
	List<String> searchByProductName(String productName);

	// search by product price
	List<Double> searchByProductPrice(double price);

	// search by product quantity
	List<Integer> searchByProductQuantity(int quantity);
	
	// create the header of table in pdf
	void writeTableHeader(PdfPTable table);

	// create the content of table in pdf
	void writeTableData(PdfPTable table, List<Long> dataId);

	// export the pdf
	void export(HttpServletResponse response, List<Long> dataId) throws DocumentException, IOException;
}