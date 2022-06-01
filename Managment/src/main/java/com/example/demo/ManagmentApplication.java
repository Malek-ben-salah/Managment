package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.Product;
import com.example.demo.model.Reservation;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;

@SpringBootApplication
public class ManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagmentApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService, ProductService productService, ReservationService reservationService,
			RoleService roleService) {
		return args -> {

			/*
			   roleService.addRole(new Role("Admin"));
			   roleService.addRole(newRole("Client"));
			   userService.addUser(newUser("mohamed","mohamed@gmail.com","mohamed"));
			   productService.addProduct(newProduct("dell", 2300, 20));
			   reservationService.addReservation(newReservation("reservation"));
			   roleService.addRoleToUser("mohamed@gmail.com","Admin");
			*/
		};

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
