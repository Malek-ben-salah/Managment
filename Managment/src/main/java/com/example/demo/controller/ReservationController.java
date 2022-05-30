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

import com.example.demo.Response.TablesResponse;
import com.example.demo.model.Reservation;
import com.example.demo.service.ReservationService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reservations/")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@PostMapping
	public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
		return ResponseEntity.ok().body(reservationService.addReservation(reservation));
	}

	@PutMapping
	public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation) {
		return ResponseEntity.ok().body(reservationService.updateReservation(reservation));
	}
	
	@GetMapping
	public ResponseEntity<TablesResponse> getAllReservations() {
		return ResponseEntity.ok().body(reservationService.getAllReservations());
	}

	@DeleteMapping("/{id}")
	public boolean deleteReservation(@PathVariable Long id) {
		return reservationService.deleteReservation(id);
	}
	
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<List<String>> searchReservationsByName(@PathVariable String name){
		return ResponseEntity.ok().body(reservationService.searchReservationsByName(name));
	}
}
