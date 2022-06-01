package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Role;

public interface RoleService {

	// add role
	Role addRole(Role role);

	// update role
	Role updateRole(Role role);

	// get all roles
	List<Role> getAllRoles();

	// delete role by id
	void deleteRole(Long id);

	// search roles by name
	List<Role> searchRolesByName(String roleName);
	
	// add role to user
	void addRoleToUser(String email,String roleName);

}
