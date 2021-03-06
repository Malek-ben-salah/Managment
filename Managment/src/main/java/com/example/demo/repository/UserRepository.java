package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	@Query("select u from User u where u.name like :name%")
	List<User> searchByName(@Param("name") String name);
	
	@Query("select u from User u where u.email like :email%")
	List<User> searchByEmail(@Param("email") String email);
	
}
