package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.Rezervacija;
import entity.Soba;
import entity.StatusRezervacije;
import entity.StatusSobe;
import managers.RezervacijeManager;
import managers.SobeManager;

public class PotvrdjivanjeRezervacijaModel extends AbstractTableModel{
	private RezervacijeManager rezervacijeManager;
	private SobeManager sobeManager;
//	#id; username; tip sobe; pocetni datum; krajnji datum; status rezervacije
	private String[] columnNames = { "Id", "Korisnicko ime", "Tip sobe", "Pocetni datum", "Krajnji datum", "Status rezervacije", "Id sobe", "Cena"};

	public PotvrdjivanjeRezervacijaModel(RezervacijeManager rezervacijeManager, SobeManager sobeManager) {
		this.rezervacijeManager = rezervacijeManager;
		this.sobeManager = sobeManager;
	}

	@Override
	public int getRowCount() {
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
			if(rez.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)) {
					rezervacije.add(rez);

			}
		}
		return rezervacije.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
			if(rez.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)) {
					rezervacije.add(rez);
	
			}
		}
		
		if(rezervacije.size() != 0) {
			Rezervacija rez = rezervacije.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return rez.getId();
			case 1:
				return rez.getUsername();
			case 2:
				return rez.getTipSobe();
			case 3:
				String formattedDate = rez.getPocetniDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				return formattedDate;
			case 4:
				formattedDate = rez.getKrajnjiDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				return formattedDate;
			case 5:
				return rez.getStatusRezervacije();
			case 6:
				return rez.getIdSobe();
			case 7:
				return rez.getCena();
			default:
				return null;
			}
		}else {
			switch (columnIndex) {
			case 0:
				return "";
			case 1:
				return "";
			case 2:
				return "";
			case 3:
				return "";
			case 4:
				return "";
			case 5:
				return "";
			case 6:
				return "";
			case 7:
				return "";
			
			default:
				return null;
			}
		}


	}

	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return this.getValueAt(0, columnIndex).getClass();
	}
}
