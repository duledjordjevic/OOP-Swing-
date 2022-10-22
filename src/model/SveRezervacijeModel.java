package model;

import java.time.format.DateTimeFormatter;

import javax.swing.table.AbstractTableModel;

import entity.Rezervacija;
import entity.StatusRezervacije;
import managers.RezervacijeManager;

public class SveRezervacijeModel extends AbstractTableModel{

	private RezervacijeManager rezervacijeManager;

	
//	#id; username; tip sobe; pocetni datum; krajnji datum; status rezervacije ; cena
	private String[] columnNames = { "Id", "Tip sobe", "Pocetni datum", "Krajnji datum", "Status rezervacije", "Cena"};

	public SveRezervacijeModel(RezervacijeManager rezervacijeManager) {
		this.rezervacijeManager = rezervacijeManager;
		
	}
	
	@Override
	public int getRowCount() {
		return rezervacijeManager.getRezervacije().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		
		if(rezervacijeManager.getRezervacije().size() != 0) {
			Rezervacija rezervacija = rezervacijeManager.getRezervacije().get(rowIndex);
			
			switch (columnIndex) {
			case 0:
				return rezervacija.getId();
			case 1:
				return rezervacija.getTipSobe();
			case 2:
				String formattedDate = rezervacija.getPocetniDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				return formattedDate;
			case 3:
				formattedDate = rezervacija.getKrajnjiDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				return formattedDate;
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
