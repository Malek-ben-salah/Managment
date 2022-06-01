package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("select R from Role R where R.name like :name%")
	List<Role> searchRoleByNames(@Param("name") String name);
	
	Role findByName(String name);
}
