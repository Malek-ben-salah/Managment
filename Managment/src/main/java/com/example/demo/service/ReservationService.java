package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.Reservation;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;

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
	List<DeleteResponse> deleteMultipeReservation(List<Long> ids);

	// search names of reservations
	List<String> searchReservationsByName(String name);

	// create the header of table in pdf
	void writeTableHeader(PdfPTable table);

	// create the content of table in pdf
	void writeTableData(PdfPTable table, List<Long> dataId);

	// export the pdf
	void export(HttpServletResponse response, List<Long> dataId) throws DocumentException, IOException;
}
