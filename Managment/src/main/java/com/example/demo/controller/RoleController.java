package com.example.demo.controller;

import java.util.List;

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

import com.example.demo.model.EmailRoleName;
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping
	public ResponseEntity<Role> addRole(@RequestBody Role role) {
		return ResponseEntity.ok().body(roleService.addRole(role));
	}

	@PutMapping
	public ResponseEntity<Role> updateRole(@RequestBody Role role) {
		return ResponseEntity.ok().body(roleService.updateRole(role));
	}

	@GetMapping
	public ResponseEntity<List<Role>> getAllRoles() {
		return ResponseEntity.ok().body(roleService.getAllRoles());
	}

	@DeleteMapping(value = "/{id}")
	public void deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
	}

	@GetMapping(value = "/name/{roleName}")
	public ResponseEntity<List<String>> searchRolesByName(@PathVariable String roleName) {
		return ResponseEntity.ok().body(roleService.searchRolesByName(roleName));
	}
	
	@PostMapping("/roletouser")
	public void addRoleToUser(@RequestBody EmailRoleName emailRoleName) {
		roleService.addRoleToUser(emailRoleName.getEmail(), emailRoleName.getRoleName());
	}
}
