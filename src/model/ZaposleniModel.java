package model;

import javax.swing.table.AbstractTableModel;

import korisnici.Zaposleni;
import managers.KorisniciManager;

public class ZaposleniModel extends AbstractTableModel {
	private static final long serialVersionUID = 173122351138550735L;
	private KorisniciManager korisniciManager;
//	#ID; tip korisnika ; ime ; prezime ; pol ; datum rodjenja ; telefon ; adresa ; korisnicko ime ; lozinka; nivoStrucneSpreme; staz
	private String[] columnNames = { "Id","Tip korisnika", "Ime", "Prezime", "Pol", "Datum rodjenja", "Telefon", "Adresa", "Korisnicko ime", "Lozinka", "Nivo strucne spreme", "Staz"};

	public ZaposleniModel(KorisniciManager korisniciManager) {
		this.korisniciManager = korisniciManager;
	}

	@Override
	public int getRowCount() {
		return korisniciManager.getZaposleni().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(korisniciManager.getZaposleni().size() != 0) {
			Zaposleni korisnik = korisniciManager.getZaposleni().get(rowIndex);
			
			switch (columnIndex) {
			case 0:
				return korisnik.getId();
			case 1:
				if(korisniciManager.getRecepcioneri().containsKey(korisnik.getUsername())) {
					return "recepcioner";
				}else {
					return "sobarica";
				}
			case 2:
				return korisnik.getIme();
			case 3:
				return korisnik.getPrezime();
			case 4:
				return korisnik.getPol();
			case 5:
				return korisnik.getDatum();
			case 6:
				return korisnik.getTelefon();
			case 7:
				return korisnik.getAdresa();
			case 8:
				return korisnik.getUsername();
			case 9:
				return korisnik.getLozinka();
			case 10:
				return korisnik.getNivoStrucneSpreme();
			case 11:
				return korisnik.getStaz();
//			case 12:
//				return korisnik.getPlata();
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
			case 9:
				return "";
			case 10:
				return "";
			case 11:
				return "";
//			case 12:
//				return "";
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
