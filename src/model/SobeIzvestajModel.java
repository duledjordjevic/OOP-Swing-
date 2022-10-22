package model;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.Rezervacija;
import entity.Soba;
import managers.RezervacijeManager;
import managers.SobeManager;

public class SobeIzvestajModel extends AbstractTableModel{
	
	private SobeManager sobeManager;
	private RezervacijeManager rezervacijeManager;
	private LocalDate pocetniDatum, krajnjiDatum;

	private String[] columnNames = { "Id sobe", "Broj nocenja", "Prihod"};

	public SobeIzvestajModel(RezervacijeManager rezervacijeManager, SobeManager sobeManager,  LocalDate pocetniDatum, LocalDate krajnjiDatum) {
		this.rezervacijeManager = rezervacijeManager;
		this.sobeManager = sobeManager;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
	}

	@Override
	public int getRowCount() {
		return sobeManager.getSobe().size();
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
		ArrayList<Integer> idSoba = new ArrayList<Integer>();
		for(Soba soba: sobeManager.getSobe()) {
			idSoba.add(soba.getId());
		}
		ArrayList<Integer> brojNocenja = new ArrayList<Integer>();
		ArrayList<Integer> prihodiPoSobi = new ArrayList<Integer>();
		
		for(int id: idSoba) {
			int count  = 0;
			int sum = 0;
			for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
				if(rez.getIdSobe() == id) {
					ArrayList<LocalDate> rezDatumi = new ArrayList<LocalDate>();
					LocalDate datum = rez.getPocetniDatum();
					while(true) {
						rezDatumi.add(datum);
						if(datum.isEqual(rez.getKrajnjiDatum())) {
							break;
						}
						datum = datum.plusDays(1);
					}
					for(LocalDate date1: trazeniDatumi) {
						if(rezDatumi.contains(date1)) {
							count++;
						}
					}
					sum += (rez.getCena()/rezDatumi.size()) * count;
				}
				
			}
			brojNocenja.add(count);
			prihodiPoSobi.add(sum);
		}
		
		
		
		if(idSoba.size() != 0) {
			
			switch (columnIndex) {
			case 0:
				return idSoba.get(rowIndex);
			case 1:
				return brojNocenja.get(rowIndex);
			case 2:
				return prihodiPoSobi.get(rowIndex);
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
