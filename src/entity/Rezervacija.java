package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rezervacija {
	private int id;
	private String username;
	private TipSobe tipSobe;
	private LocalDate pocetniDatum;
	private LocalDate krajnjiDatum;
	private StatusRezervacije statusRezervacije;
	private int cena;
	private int idSobe;
	private String sobaricaIme;
	
	public Rezervacija() {
		
	}
	
	public Rezervacija(int id, String username, TipSobe tipSobe, LocalDate pocetniDatum, LocalDate krajnjiDatum, StatusRezervacije statusRezervacije, int cena, int idSobe, String sobaricaIme) {
		super();
		this.id = id;
		this.username = username;
		this.tipSobe = tipSobe;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.statusRezervacije = statusRezervacije;
		this.cena = cena;
		this.idSobe = idSobe;
		this.sobaricaIme = sobaricaIme;
		
	}
	public String toFileString() {
		String datum1 = pocetniDatum.format(DateTimeFormatter.ofPattern("d.MM.yyyy"));
		String datum2 = krajnjiDatum.format(DateTimeFormatter.ofPattern("d.MM.yyyy"));
		return id +" ; " + username + " ; " + tipSobe + " ; " + datum1 + " ; " + datum2 + " ; " + statusRezervacije + " ; " + cena + " ; " + idSobe + " ; " + sobaricaIme;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public TipSobe getTipSobe() {
		return tipSobe;
	}

	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}

	public LocalDate getPocetniDatum() {
		return pocetniDatum;
	}

	public void setPocetniDatum(LocalDate pocetniDatum) {
		this.pocetniDatum = pocetniDatum;
	}

	public LocalDate getKrajnjiDatum() {
		return krajnjiDatum;
	}

	public void setKrajnjiDatum(LocalDate krajnjiDatum) {
		this.krajnjiDatum = krajnjiDatum;
	}

	public StatusRezervacije getStatusRezervacije() {
		return statusRezervacije;
	}

	public void setStatusRezervacije(StatusRezervacije statusRezervacije) {
		this.statusRezervacije = statusRezervacije;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public int getIdSobe() {
		return idSobe;
	}

	public void setIdSobe(int idSobe) {
		this.idSobe = idSobe;
	}

	public String getSobaricaIme() {
		return sobaricaIme;
	}

	public void setSobaricaIme(String sobaricaIme) {
		this.sobaricaIme = sobaricaIme;
	}
	
	
	
}
