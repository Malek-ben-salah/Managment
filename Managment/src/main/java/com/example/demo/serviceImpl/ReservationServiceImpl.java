package com.example.demo.serviceImpl;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import com.example.demo.Response.DeleteResponse;
import com.example.demo.Response.TablesResponse;
import com.example.demo.model.Reservation;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.ReservationService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

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
	public TablesResponse getAllReservations(int page) {
		System.out.println("*** getting all reservations ***");
		TablesResponse res=new TablesResponse();
		List<String> columnsName=new ArrayList<>();
		res.setTitle("List of reservations");
		columnsName.add("id");
		columnsName.add("reservation name");
		res.setColmuns(columnsName);
		res.setData(reservationRepository.findAll(PageRequest.of(0, page)).toList());
		return res;
	}

	@Override
	public boolean deleteReservation(Long id) {
		System.out.println("deleting reservation by id: "+id);
		reservationRepository.deleteById(id);
		return true;
	}

	@Override
	public List<DeleteResponse> deleteMultipeReservation(List<Long> ids) {
		System.out.println("delete multipe reservations : "+ids);
		List<DeleteResponse> deleteResponses = new ArrayList<DeleteResponse>();
		List<Long> inexistantIds = new ArrayList<Long>();
		List<Reservation> reservations = reservationRepository.findAllById(ids);
		List<Long> existantId = new ArrayList<Long>();
		reservations.forEach(res -> {
			existantId.add(res.getId());
		});
		try {
			if (ids == null || ids.isEmpty()) {
				deleteResponses.add(new DeleteResponse("delete failed ! ", "error"));
				System.out.println("array of ids is null or empty ! ");
			}
		} catch (HttpMessageNotReadableException e) {
			System.out.println(e.getMessage());
		}

		if (reservations.size() < ids.size()) {
			for (Long id : ids) {
				if (!existantId.contains(id)) {
					inexistantIds.add(id);
				}
			}

			deleteResponses.add(new DeleteResponse("delete failed for those users ids: " + inexistantIds, "error"));
		}
		if (!reservations.isEmpty()) {
			deleteResponses.add(new DeleteResponse("delete success", "success"));
			System.out.println(reservationRepository.findAllById(ids));
			reservationRepository.deleteAllInBatch(reservations);
		}
		return deleteResponses;
	}

	@Override
	public List<String> searchReservationsByName(String name) {
		System.out.println("search reservations by name: "+name);
		return reservationRepository.searchReservationByName(name);
	}

	@Override
	public void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		cell.setPhrase(new Phrase("reservation_id", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("reservation_name", font));
		table.addCell(cell);
	}

	@Override
	public void writeTableData(PdfPTable table, List<Long> dataId) {
		List<Reservation> listReservations = reservationRepository.findAllById(dataId);
		for (Reservation reservation : listReservations) {
			table.addCell(reservation.getId().toString());
			table.addCell(reservation.getName());
		}
	}

	@Override
	public void export(HttpServletResponse response, List<Long> dataId) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		Paragraph p = new Paragraph("List of reservations", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 6.75f, 6.75f });
		table.setSpacingBefore(10);
		writeTableHeader(table);
		writeTableData(table, dataId);
		document.add(table);
		document.close();	
	}

}
