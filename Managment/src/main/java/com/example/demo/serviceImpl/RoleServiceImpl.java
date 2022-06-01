package com.example.demo.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.AlreadyExistException;
import com.example.demo.exception.EmailNotFound;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Role addRole(Role role) {
		System.out.println("adding role: " + role.getName());
		Role roleByname=roleRepository.findByName(role.getName());
		if(roleByname != null) {
			throw new AlreadyExistException("role already exist !");
		}
		return roleRepository.save(role);
	}

	@Override
	public Role updateRole(Role role) {
		System.out.println("updating role: " + role.getName());
		return roleRepository.save(role);
	}

	@Override
	public List<Role> getAllRoles() {
		System.out.println("getting all roles ... ");
		return roleRepository.findAll();
	}

	@Override
	public void deleteRole(Long id) {
		System.out.println("deleting role by id: " + id);
		roleRepository.deleteById(id);
	}

	@Override
	public List<String> searchRolesByName(String roleName) {
		System.out.println("search roles by name: " + roleName);
		return roleRepository.searchRoleByNames(roleName);
	}

	@Override
	public void addRoleToUser(String email, String roleName) {
		System.out.println("add role : "+roleName+" to user with email: "+email);
		User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFound("email not found"));
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
	}

}
