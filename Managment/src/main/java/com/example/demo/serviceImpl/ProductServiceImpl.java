package com.example.demo.serviceImpl;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

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
		columnsName.add("id");
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
	public List<Product> searchByProductName(String productName) {
		System.out.println("search by product name: " + productName);
		return productRepository.searchByProductName(productName);
	}

	@Override
	public List<Product> searchByProductPrice(double price) {
		System.out.println("search by product price: " + price);
		return productRepository.searchByProductPrice(price);
	}

	@Override
	public List<Product> searchByProductQuantity(int quantity) {
		System.out.println("search by product quantity: " + quantity);
		return productRepository.searchByProductQuantity(quantity);
	}

	@Override
	public void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		cell.setPhrase(new Phrase("product_id", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("product_name", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("price", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("quantity", font));
		table.addCell(cell);
	}

	@Override
	public void writeTableData(PdfPTable table, List<Long> dataId) {
		List<Product> listProducts = productRepository.findAllById(dataId);
		for (Product product : listProducts) {
			table.addCell(product.getId().toString());
			table.addCell(product.getProductName());
			table.addCell(Double.toString(product.getPrice()));
			table.addCell(Integer.toString(product.getQuantity()));
		}
	}

	@Override
	public void export(HttpServletResponse response, List<Long> dataId) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		Paragraph p = new Paragraph("List of Products", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.0f, 3.0f, 3.0f, 3.0f });
		table.setSpacingBefore(10);
		writeTableHeader(table);
		writeTableData(table, dataId);
		document.add(table);
		document.close();
		
		
	}

}
