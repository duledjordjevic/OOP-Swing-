package model;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.Rezervacija;
import entity.Soba;
import managers.KorisniciManager;
import managers.RezervacijeManager;

public class BrojSpremljenihSobaSobaricaModel extends AbstractTableModel{

	private RezervacijeManager rezervacijeManager;
	private KorisniciManager korisniciManager;
	private LocalDate pocetniDatum, krajnjiDatum;

	private String[] columnNames = { "Ime", "Broj spremljenih soba"};

	public BrojSpremljenihSobaSobaricaModel(RezervacijeManager rezervacijeManager, KorisniciManager korisniciManager, LocalDate pocetniDatum, LocalDate krajnjiDatum) {
		this.korisniciManager = korisniciManager;
		this.rezervacijeManager = rezervacijeManager;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
	}

	@Override
	public int getRowCount() {
		return korisniciManager.getSobarice().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ArrayList<LocalDate> trazeniDatumi = new ArrayList<LocalDate>();
		LocalDate date = pocetniDatum;
		while(true) {
			trazeniDatumi.add(date);
			if(date.isEqual(krajnjiDatum)) {
				break;
			}
			date = date.plusDays(1);
		}
		ArrayList<String> imenaSobarica = new ArrayList<String>();
		ArrayList<Integer> brojSpremljenihSoba = new ArrayList<Integer>();
		for(String username: korisniciManager.getSobarice().keySet()) {
			imenaSobarica.add(username);
		}
		for(String username: imenaSobarica) {
			int count = 0;
			for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
				if(trazeniDatumi.contains(rez.getKrajnjiDatum()) & rez.getSobaricaIme().trim().equals(username)) {
					count++;
				}
			}
			brojSpremljenihSoba.add(count);
		}
		
		
		
		if(imenaSobarica.size() != 0) {
			
			switch (columnIndex) {
			case 0:
				return imenaSobarica.get(rowIndex);
			case 1:
				return brojSpremljenihSoba.get(rowIndex);
			default:
				return null;
			}
		}else {
			switch (columnIndex) {
			case 0:
				return "";
			case 1:
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
