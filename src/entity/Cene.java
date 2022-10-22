package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Cene {
	
	private int id;
	private int jednokrevetnaSoba;
	private int dvokrevetnaSaJednimLezajemSoba;
	private int dvokrevetnaSaDvaLezajaSoba;
	private int trokrevetnaSoba;
	private int dorucak;
	private int rucak;
	private int vecera;
	private int spaCentar;
	private LocalDate pocetniDatum;
	private LocalDate krajnjiDatum;
	private ArrayList<LocalDate> datumi;
	private boolean isActive;
	
	public Cene() {
		
	}
	public Cene(int id, int jednokrevetnaSoba, int dvokrevetnaSaJednimLezajemSoba, int dvokrevetnaSaDvaLezajaSoba, int trokrevetnaSoba
			, int dorucak, int rucak, int vecera, int spaCentar, LocalDate pocetniDatum, LocalDate krajnjiDatum, boolean isActive) {
		super();
		this.id = id;
		this.jednokrevetnaSoba = jednokrevetnaSoba;
		this.dvokrevetnaSaJednimLezajemSoba = dvokrevetnaSaJednimLezajemSoba;
		this.dvokrevetnaSaDvaLezajaSoba = dvokrevetnaSaDvaLezajaSoba;
		this.trokrevetnaSoba = trokrevetnaSoba;
		this.dorucak = dorucak;
		this.rucak = rucak;
		this.vecera = vecera;
		this.spaCentar = spaCentar;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.isActive = isActive;
		LocalDate datum = pocetniDatum;
		datumi = new ArrayList<LocalDate>();
		while(true) {
			datumi.add(datum);
			if(datum.isEqual(krajnjiDatum)) {
				break;
			}
			datum = datum.plusDays(1);
		}
	}
	public String toFileString() {
//		#id; JEDNOKREVETNA ; DVOKREVETNA_SA_JEDNIM_LEZAJEM; DVOKREVETNA_SA_DVA_LEZAJA; TROKREVETNA; dorucak; rucak; vecera; spa centar; pocetni datum; krajnji datum
		String datum1 = pocetniDatum.format(DateTimeFormatter.ofPattern("d.MM.yyyy"));
		String datum2 = krajnjiDatum.format(DateTimeFormatter.ofPattern("d.MM.yyyy"));
		String aktivan;
		if(isActive) {
			aktivan = "da";
		}else {
			aktivan = "ne";
		}
		return id +" ; " + jednokrevetnaSoba + " ; " + dvokrevetnaSaJednimLezajemSoba + " ; " + dvokrevetnaSaDvaLezajaSoba + " ; " + trokrevetnaSoba + " ; " + dorucak + " ; " + rucak + " ; " + vecera + " ; " + spaCentar + " ; " + datum1 + " ; " + datum2 + " ; " + aktivan  ;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getJednokrevetnaSoba() {
		return jednokrevetnaSoba;
	}
	public void setJednokrevetnaSoba(int jednokrevetnaSoba) {
		this.jednokrevetnaSoba = jednokrevetnaSoba;
	}
	public int getDvokrevetnaSaJednimLezajemSoba() {
		return dvokrevetnaSaJednimLezajemSoba;
	}
	public void setDvokrevetnaSaJednimLezajemSoba(int dvokrevetnaSaJednimLezajemSoba) {
		this.dvokrevetnaSaJednimLezajemSoba = dvokrevetnaSaJednimLezajemSoba;
	}
	public int getDvokrevetnaSaDvaLezajaSoba() {
		return dvokrevetnaSaDvaLezajaSoba;
	}
	public void setDvokrevetnaSaDvaLezajaSoba(int dvokrevetnaSaDvaLezajaSoba) {
		this.dvokrevetnaSaDvaLezajaSoba = dvokrevetnaSaDvaLezajaSoba;
	}
	public int getTrokrevetnaSoba() {
		return trokrevetnaSoba;
	}
	public void setTrokrevetnaSoba(int trokrevetnaSoba) {
		this.trokrevetnaSoba = trokrevetnaSoba;
	}
	public int getDorucak() {
		return dorucak;
	}
	public void setDorucak(int dorucak) {
		this.dorucak = dorucak;
	}
	public int getRucak() {
		return rucak;
	}
	public void setRucak(int rucak) {
		this.rucak = rucak;
	}
	public int getVecera() {
		return vecera;
	}
	public void setVecera(int vecera) {
		this.vecera = vecera;
	}
	public int getSpaCentar() {
		return spaCentar;
	}
	public void setSpaCentar(int spaCentar) {
		this.spaCentar = spaCentar;
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
	public ArrayList<LocalDate> getDatumi() {
		return datumi;
	}
	public void setDatumi(ArrayList<LocalDate> datumi) {
		this.datumi = datumi;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
