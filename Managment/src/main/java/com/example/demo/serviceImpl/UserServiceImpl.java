package com.example.demo.serviceImpl;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.exception.AlreadyExistException;
import com.example.demo.exception.EmailFormatException;
import com.example.demo.exception.EmailNotFound;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
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
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User addUser(User user) {
		System.out.println("adding user : " + user.getName());
		//test if this email exist for another user 
		User userEmail = userRepository.findByEmail(user.getEmail()).orElse(null);
		if (userEmail != null) {
			throw new AlreadyExistException("this email : " + user.getEmail() + " is already exist try another one");
		}
		if (!(user.getEmail().contains("@"))) {
			throw new EmailFormatException("form of this email is wrong");
		}
		//encode password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		System.out.println("updating user : " + user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public boolean deleteUser(Long id) {
		System.out.println("deleting user by id: " + id);
		userRepository.deleteById(id);
		return true;
	}

	@Override
	public TablesResponse getAllUsers() {
		System.out.println("getting all users ...");
		TablesResponse res = new TablesResponse();
		// list of columns name 
		List<String> columnsName = new ArrayList<>();
		columnsName.add("id");
		columnsName.add("name");
		columnsName.add("email");
		columnsName.add("password");
		columnsName.add("roles");
		res.setTitle("List of Users");
		res.setColmuns(columnsName);
		// list of users
		res.setData(userRepository.findAll());
		return res;
	}

	@Override
	public User getUserById(Long id) {
		System.out.println("getting user by id: " + id);
		return userRepository.findById(id).get();
	}

	@Override
	public User getUserByEmail(String email) {
		System.out.println("getting user by email : " + email);
		return userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFound("this email doesn't exist "));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new EmailNotFound("this email doesn't exist "));
		System.out.println("email found in the database: << email >> ");
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public List<DeleteResponse> deleteMultipeUsers(List<Long> ids) {
		System.out.println("delete multipe users : " + ids);
		List<DeleteResponse> deleteResponses = new ArrayList<DeleteResponse>();	
		List<Long> inexistantIds = new ArrayList<Long>();
		List<User> users = userRepository.findAllById(ids);
		List<Long> existantId = new ArrayList<Long>();
		users.forEach(user -> {
			existantId.add(user.getId());
		});
		try {
			if (ids == null || ids.isEmpty()) {
				deleteResponses.add(new DeleteResponse("delete failed ! ", "error"));
				System.out.println("array of ids is null or empty ! ");
			}
		} catch (HttpMessageNotReadableException e) {
			System.out.println(e.getMessage());
		}

		if (users.size() < ids.size()) {
			for (Long id : ids) {
				if (!existantId.contains(id)) {
					inexistantIds.add(id);
				}
			}

			deleteResponses.add(new DeleteResponse("delete failed for those users ids: " + inexistantIds, "error"));
		}
		if (!users.isEmpty()) {
			deleteResponses.add(new DeleteResponse("delete success", "success"));
			System.out.println(userRepository.findAllById(ids));
			userRepository.deleteAllInBatch(users);
		}
		return deleteResponses;
	}

	@Override
	public List<User> searchByNames(String name) {
		System.out.println("search by name: " + name);
		return userRepository.searchByName(name);
	}

	@Override
	public List<User> searchByEmails(String email) {
		System.out.println("search by email: " + email);
		return userRepository.searchByEmail(email);
	}

	@Override
	public void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		cell.setPhrase(new Phrase("user_id", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("name", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("email", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("password", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("roles", font));
		table.addCell(cell);
	}

	@Override
	public void writeTableData(PdfPTable table, List<Long> dataId) {
		List<User> listUsers = userRepository.findAllById(dataId);
		for (User user : listUsers) {
			table.addCell(user.getId().toString());
			table.addCell(user.getName());
			table.addCell(user.getEmail());
			table.addCell(user.getPassword());
			table.addCell(user.getRoles().toString());
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
		Paragraph p = new Paragraph("List of Users", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.0f, 1.5f, 3.5f, 5.0f, 3.5f });
		table.setSpacingBefore(10);
		writeTableHeader(table);
		writeTableData(table, dataId);
		document.add(table);
		document.close();
		
	}

}
