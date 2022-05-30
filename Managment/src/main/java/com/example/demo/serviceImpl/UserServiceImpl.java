package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
		User userEmail = userRepository.findByEmail(user.getEmail()).orElse(null);
		if (userEmail != null) {
			throw new AlreadyExistException("this email : " + user.getEmail() + " is already exist try another one");
		}
		if (!(user.getEmail().contains("@"))) {
			throw new EmailFormatException("form of this email is wrong");
		}
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
	public TablesResponse getAllUsers(int page) {
		System.out.println("getting all users ...");
		TablesResponse res = new TablesResponse();
		List<String> columnsName = new ArrayList<>();
		columnsName.add("name");
		columnsName.add("email");
		columnsName.add("password");
		columnsName.add("roles");
		res.setTitle("List of Users");
		res.setColmuns(columnsName);
		res.setData(userRepository.findAll(PageRequest.of(0, page)).toList());
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
	public List<String> searchByNames(String name) {
		System.out.println("search by name: " + name);
		return userRepository.searchByName(name);
	}

	@Override
	public List<String> searchByEmails(String email) {
		System.out.println("search by email: " + email);
		return userRepository.searchByEmail(email);
	}

}
