package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.User;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;

public interface UserService {

	// add user to database
	User addUser(User user);

	// update user
	User updateUser(User user);

	// delete User by id
	boolean deleteUser(Long id);

	// get all the users
	TablesResponse getAllUsers();

	// get user by id
	User getUserById(Long id);

	// get user by email
	User getUserByEmail(String email);

	// Delete multipe users
	List<DeleteResponse> deleteMultipeUsers(List<Long> ids);

	// search by name
	List<String> searchByNames(String name);

	// search by email
	List<String> searchByEmails(String email);

	// create the header of table in pdf
	void writeTableHeader(PdfPTable table);

	// create the content of table in pdf
	void writeTableData(PdfPTable table, List<Long> dataId);

	// export the pdf
	void export(HttpServletResponse response, List<Long> dataId) throws DocumentException, IOException;
}
