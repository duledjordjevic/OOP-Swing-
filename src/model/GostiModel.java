package model;

import javax.swing.table.AbstractTableModel;

import korisnici.Gost;
import managers.KorisniciManager;

public class GostiModel extends AbstractTableModel{

	private static final long serialVersionUID = 8425556242324130506L;
	private KorisniciManager korisniciManager;
//	#ID; tip korisnika ; ime ; prezime ; pol ; datum rodjenja ; telefon ; adresa ; korisnicko ime ; lozinka; nivoStrucneSpreme; staz
	private String[] columnNames = { "Id", "Ime", "Prezime", "Pol", "Datum rodjenja", "Telefon", "Adresa", "Korisnicko ime", "Lozinka"};

	public GostiModel(KorisniciManager korisniciManager) {
		this.korisniciManager = korisniciManager;
	}

	@Override
	public int getRowCount() {
		return korisniciManager.getGosti().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(korisniciManager.getGosti().size() != 0) {
			Gost gost = korisniciManager.getGosti().get(rowIndex);
			
			switch (columnIndex) {
			case 0:
				return gost.getId();
			case 1:
				return gost.getIme();
			case 2:
				return gost.getPrezime();
			case 3:
				return gost.getPol();
			case 4:
				return gost.getDatum();
			case 5:
				return gost.getTelefon();
			case 6:
				return gost.getAdresa();
			case 7:
				return gost.getUsername();
			case 8:
				return gost.getLozinka();
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
			case 8:
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
