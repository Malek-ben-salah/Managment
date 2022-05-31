package com.example.demo.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.lowagie.text.DocumentException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> addUser(@RequestBody User user) {
		return ResponseEntity.ok().body(userService.addUser(user));
	}

	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		return ResponseEntity.ok().body(userService.updateUser(user));
	}

	@GetMapping
	public ResponseEntity<TablesResponse> getAllUsers() {
		return ResponseEntity.ok().body(userService.getAllUsers());
	}

	@GetMapping(value = "/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		return ResponseEntity.ok().body(userService.getUserByEmail(email));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok().body(userService.getUserById(id));
	}

	@DeleteMapping(value = "/{id}")
	public boolean deleteUserById(@PathVariable Long id) {
		return userService.deleteUser(id);
	}

	@DeleteMapping
	public ResponseEntity<List<DeleteResponse>> deleteMultipeUsers(@RequestBody List<Long> ids) {
		return ResponseEntity.ok().body(userService.deleteMultipeUsers(ids));
	}

	@GetMapping(value = "/searchname/{name}")
	public ResponseEntity<List<String>> searchByNames(@PathVariable String name) {
		return ResponseEntity.ok().body(userService.searchByNames(name));
	}

	@GetMapping(value = "/searchemail/{email}")
	public ResponseEntity<List<String>> searchByEmails(@PathVariable String email) {
		return ResponseEntity.ok().body(userService.searchByEmails(email));
	}

	@GetMapping(value = "/export/pdf/")
	public void exportPdf(@RequestBody List<Long> ids, HttpServletResponse response)
			throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);
		userService.export(response, ids);
	}
}
