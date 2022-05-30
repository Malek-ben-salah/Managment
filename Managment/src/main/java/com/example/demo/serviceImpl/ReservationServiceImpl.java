package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.Reservation;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.ReservationService;

@Service @Transactional
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public Reservation addReservation(Reservation reservation) {
		System.out.println("adding reservation :"+reservation.getName());
		return reservationRepository.save(reservation);
	}

	@Override
	public Reservation updateReservation(Reservation reservation) {
		System.out.println("updating reservation :"+reservation.getName());
		return reservationRepository.save(reservation);		
	}

	@Override
	public TablesResponse getAllReservations() {
		System.out.println("*** getting all reservations ***");
		TablesResponse res=new TablesResponse();
		List<String> columnsName=new ArrayList<>();
		res.setTitle("List of reservations");
		columnsName.add("reservation name");
		res.setColmuns(columnsName);
		res.setData(reservationRepository.findAll());
		return res;
	}

	@Override
	public boolean deleteReservation(Long id) {
		System.out.println("deleting reservation by id: "+id);
		reservationRepository.deleteById(id);
		return true;
	}

	@Override
	public DeleteResponse deleteMultipeReservation(List<Long> ids) {
		System.out.println("delete multipe reservations : "+ids);
		DeleteResponse deleteResponse=new DeleteResponse();
		try {
			reservationRepository.deleteAllById(ids);
			deleteResponse.setMessage("delete success");
			deleteResponse.setColor("green");
		} catch (EmptyResultDataAccessException e) {
			System.out.println(e.getMessage());
			deleteResponse.setMessage("delete failed ! ");
			deleteResponse.setColor("red");
		}
		return deleteResponse;
	}

	@Override
	public List<String> searchReservationsByName(String name) {
		System.out.println("search reservations by name: "+name);
		return reservationRepository.searchReservationByName(name);
	}

}
