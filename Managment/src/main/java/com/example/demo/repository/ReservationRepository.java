package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("select R from Reservation R where R.name like :name%")
	List<Reservation> searchReservationByName(@Param("name") String name);
}
