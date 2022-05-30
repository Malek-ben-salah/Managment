package com.example.demo.service;

import java.util.List;

import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.Reservation;

public interface ReservationService {

	// add reservation
	Reservation addReservation(Reservation reservation);

	// update reservation
	Reservation updateReservation(Reservation reservation);

	// get all reservations
	TablesResponse getAllReservations();

	// delete reservation
	boolean deleteReservation(Long id);

	// delete multipe reservation
	DeleteResponse deleteMultipeReservation(List<Long> ids);

	// search names of reservations
	List<String> searchReservationsByName(String name);
}
