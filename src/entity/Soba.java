package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Soba {
	private int id;
	private TipSobe tipSobe;
	private StatusSobe statusSobe;
	private boolean klima;
	private boolean balkon;
	private boolean tv;
	private ArrayList<LocalDate> slobodniDatumi;
	
	public Soba() {
		
	}
	
	public Soba(int id, TipSobe tipSobe, StatusSobe statusSobe,boolean klima, boolean balkon, boolean tv,  ArrayList<LocalDate> datumi) {
		super();
		this.id = id;
		this.tipSobe = tipSobe;
		this.statusSobe = statusSobe;
		this.klima = klima;
		this.balkon = balkon;
		this.tv = tv;
		this.slobodniDatumi = datumi;
	}
	
	public String toFileString() {

		String slobodniDatumiString = "";
		for(LocalDate datum: slobodniDatumi) {
			String datum1 = datum.format(DateTimeFormatter.ofPattern("d.MM.yyyy"));
			slobodniDatumiString += datum1 + "//";
			slobodniDatumiString = slobodniDatumiString.substring(0, slobodniDatumiString.length() - 1);
		}
		if(slobodniDatumiString.equals("")) {
			slobodniDatumiString = "/";
		}
		if(klima & balkon & tv) {
			return id +" ; " + tipSobe + " ; " + statusSobe + " ; "  + "da" + " ; " + "da" + " ; " + "da" + " ; " + slobodniDatumiString ;
		}else if(klima & balkon & !tv) {
			return id +" ; " + tipSobe + " ; " + statusSobe + " ; "  + "da" + " ; " + "da" + " ; " + "ne" + " ; " + slobodniDatumiString ;
		}else if(klima & !balkon & tv) {
			return id +" ; " + tipSobe + " ; " + statusSobe + " ; "  + "da" + " ; " + "ne" + " ; " + "da" + " ; " + slobodniDatumiString ;
		}else if(klima & !balkon & !tv) {
			return id +" ; " + tipSobe + " ; " + statusSobe + " ; "  + "da" + " ; " + "ne" + " ; " + "ne" + " ; " + slobodniDatumiString ;
		}else if(!klima & balkon & tv) {
			return id +" ; " + tipSobe + " ; " + statusSobe + " ; "  + "ne" + " ; " + "da" + " ; " + "da" + " ; " + slobodniDatumiString ;
		}else if(!klima & !balkon & tv) {
			return id +" ; " + tipSobe + " ; " + statusSobe + " ; "  + "ne" + " ; " + "ne" + " ; " + "da" + " ; " + slobodniDatumiString ;
		}else if(!klima & balkon & !tv) {
			return id +" ; " + tipSobe + " ; " + statusSobe + " ; "  + "ne" + " ; " + "da" + " ; " + "ne" + " ; " + slobodniDatumiString ;
		}else if(!klima & !balkon & !tv) {
			return id +" ; " + tipSobe + " ; " + statusSobe + " ; "  + "ne" + " ; " + "ne" + " ; " + "ne" + " ; " + slobodniDatumiString ;
		}
		return null;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipSobe getTipSobe() {
		return tipSobe;
	}

	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}

	public StatusSobe getStatusSobe() {
		return statusSobe;
	}

	public void setStatusSobe(StatusSobe statusSobe) {
		this.statusSobe = statusSobe;
	}

	public ArrayList<LocalDate> getSlobodniDatumi() {
		return slobodniDatumi;
	}

	public void setSlobodniDatumi(ArrayList<LocalDate> slobodniDatumi) {
		this.slobodniDatumi = slobodniDatumi;
	}

	public boolean isKlima() {
		return klima;
	}

	public void setKlima(boolean klima) {
		this.klima = klima;
	}

	public boolean isBalkon() {
		return balkon;
	}

	public void setBalkon(boolean balkon) {
		this.balkon = balkon;
	}

	public boolean isTv() {
		return tv;
	}

	public void setTv(boolean tv) {
		this.tv = tv;
	}
	
	
	
	
	
	
	
}
