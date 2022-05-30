package com.example.demo.service;

import java.util.List;

import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.User;

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
}
