package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.Rezervacija;
import entity.StatusRezervacije;
import korisnici.Gost;
import managers.RezervacijeManager;

public class GostRezervacijeModel extends AbstractTableModel {
	
	
	private static final long serialVersionUID = -2060459727225205794L;
	private RezervacijeManager rezervacijeManager;
	private Gost gost;
	
//	#id; username; tip sobe; pocetni datum; krajnji datum; status rezervacije
	private String[] columnNames = { "Id", "Tip sobe", "Pocetni datum", "Krajnji datum", "Status rezervacije", "Cena"};

	public GostRezervacijeModel(RezervacijeManager rezervacijeManager, Gost gost) {
		this.rezervacijeManager = rezervacijeManager;
		this.gost = gost;
		
	}
	
	@Override
	public int getRowCount() {
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		for (Rezervacija rezervacija: rezervacijeManager.getRezervacije()) {
			if(rezervacija.getUsername().equals(gost.getUsername())) {
				rezervacije.add(rezervacija);
//				System.out.println(rezervacija.toFileString());
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
		for (Rezervacija rezervacija: rezervacijeManager.getRezervacije()) {
			if(rezervacija.getUsername().equals(gost.getUsername())) {
				rezervacije.add(rezervacija);
				
			}
		}
		if(rezervacije.size() != 0) {
			Rezervacija rezervacija = rezervacije.get(rowIndex);
			
			switch (columnIndex) {
			case 0:
				return rezervacija.getId();
			case 1:
				return rezervacija.getTipSobe();
			case 2:
				return rezervacija.getPocetniDatum();
			case 3:
				return rezervacija.getKrajnjiDatum();
			case 4:
				return rezervacija.getStatusRezervacije();
			case 5:
				if(rezervacija.getStatusRezervacije().equals(StatusRezervacije.ODBIJENA) ||
						rezervacija.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)) {
					return 0;
				}else {
					return rezervacija.getCena();
				}
				
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
