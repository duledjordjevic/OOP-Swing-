package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IzvestajRezervacija {
	
	private int id;
	private int idRezervacije;
	private LocalDate datum;
	private StatusRezervacije statusRezervacije;
	
	public IzvestajRezervacija() {
		
	}
	
	public IzvestajRezervacija(int id, int idRezervacije, LocalDate datum, StatusRezervacije statusRezervacije) {
		this.id = id;
		this.idRezervacije = idRezervacije;
		this.datum = datum;
		this.statusRezervacije = statusRezervacije;
	}
	
	public String toFileString() {
		String date = datum.format(DateTimeFormatter.ofPattern("d.MM.yyyy"));
		return id +" ; " + idRezervacije + " ; " + date + " ; " + statusRezervacije ;
	}

	public int getIdRezervacije() {
		return idRezervacije;
	}

	public void setIdRezervacije(int idRezervacije) {
		this.idRezervacije = idRezervacije;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public StatusRezervacije getStatusRezervacije() {
		return statusRezervacije;
	}

	public void setStatusRezervacije(StatusRezervacije statusRezervacije) {
		this.statusRezervacije = statusRezervacije;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
