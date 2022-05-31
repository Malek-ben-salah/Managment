package com.example.demo.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repository.UserRepository;

@SpringBootTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void CansearchByEmail() {
		
		String email="client@gmail.com";
		
		List<String> emails=userRepository.searchByEmail(email);
		
		assertThat(emails).contains(email);
	}

}
