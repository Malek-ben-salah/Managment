package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Role;
import com.example.demo.service.RoleService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping
	public Role addRole(@RequestBody Role role) {
		return roleService.addRole(role);
	}

	@PutMapping
	public Role updateRole(@RequestBody Role role) {
		return roleService.updateRole(role);
	}

	@GetMapping
	public List<Role> getAllRoles() {
		return roleService.getAllRoles();
	}

	@DeleteMapping(value = "/{id}")
	public void deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
	}
}
