package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.Rezervacija;
import entity.Soba;
import entity.StatusSobe;
import korisnici.Sobarica;
import managers.RezervacijeManager;
import managers.SobeManager;

public class SobaricaSpremanjeModel extends AbstractTableModel{
	
	private SobeManager sobeManager;
	private RezervacijeManager rezervacijeManager;
	private Sobarica sobarica;
//	#id; tip sobe; status sobe; slobodni datumi
	private String[] columnNames = { "Id", "Tip sobe",  "Status sobe", "Klima" , "Balkon", "Tv"};

	public SobaricaSpremanjeModel(SobeManager sobeManager,  RezervacijeManager rezervacijeManager, Sobarica sobarica) {
		this.sobeManager = sobeManager;
		this.rezervacijeManager = rezervacijeManager;
		this.sobarica = sobarica;
	}

	@Override
	public int getRowCount() {
		ArrayList<Soba> sobe = new ArrayList<Soba>();
		for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
			if(rez.getSobaricaIme().equals(sobarica.getUsername())) {
				Soba soba = sobeManager.pronadjiSobuPoId(rez.getIdSobe());
				if(soba.getStatusSobe().equals(StatusSobe.SPREMANJE)) {
					sobe.add(soba);
				}
				
			}
		}
		return sobe.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ArrayList<Soba> sobe = new ArrayList<Soba>();
		for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
			if(rez.getSobaricaIme().equals(sobarica.getUsername())) {
				Soba soba = sobeManager.pronadjiSobuPoId(rez.getIdSobe());
				if(soba.getStatusSobe().equals(StatusSobe.SPREMANJE)) {
					sobe.add(soba);
				}
			}
		}
		if(sobe.size() != 0) {
			Soba soba = sobe.get(rowIndex);
			
			switch (columnIndex) {
			case 0:
				return soba.getId();
			case 1:
				return soba.getTipSobe();
			case 2:
				return soba.getStatusSobe();
			case 3:
				if(soba.isKlima()) {
					return "da";
				}else {
					return "ne";
				}
			case 4:
				if(soba.isBalkon()) {
					return "da";
				}else {
					return "ne";
				}
			case 5:
				if(soba.isTv()) {
					return "da";
				}else {
					return "ne";
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
