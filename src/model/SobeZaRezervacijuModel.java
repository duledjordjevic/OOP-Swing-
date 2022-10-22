package model;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.Soba;
import entity.TipSobe;
import managers.SobeManager;

public class SobeZaRezervacijuModel extends AbstractTableModel{

	private SobeManager sobeManager;
	private TipSobe tipSobe;
	private LocalDate pocetniDatum;
	private LocalDate krajnjiDatum;
	private boolean klima, balkon, tv;
	
//	#id; tip sobe; status sobe; slobodni datumi
	private String[] columnNames = { "Id", "Tip sobe",  "Status sobe", "Klima" , "Balkon", "Tv"};

	public SobeZaRezervacijuModel(SobeManager sobeManager, TipSobe tipSobe, LocalDate pocetniDatum,
		LocalDate krajnjiDatum, boolean klima, boolean balkon, boolean tv) {
		this.sobeManager = sobeManager;
		this.tipSobe = tipSobe;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.klima = klima;
		this.balkon = balkon;
		this.tv = tv;
		
	}

	@Override
	public int getRowCount() {
		ArrayList<Soba> trazeneSobe1 = new ArrayList<Soba>();
		ArrayList<Soba> trazeneSobe = new ArrayList<Soba>();
		for(Soba soba: sobeManager.getSobe()) {
			
			if(soba.getTipSobe().equals(tipSobe)) {
				if(klima) {
					if(balkon) {
						if(tv) {
							if(soba.isKlima() & soba.isBalkon() & soba.isTv()) {
								trazeneSobe1.add(soba);
							}
						}else {
							if(soba.isKlima() & soba.isBalkon()) {
								trazeneSobe1.add(soba);
							}
						}
					}else {
						if(tv) {
							if(soba.isKlima()  & soba.isTv()) {
								trazeneSobe1.add(soba);
							}
						}else {
							if(soba.isKlima() ) {
								trazeneSobe1.add(soba);
							}
						}
					}
				}else {
					if(balkon) {
						if(tv) {
							if( soba.isBalkon() & soba.isTv()) {
								trazeneSobe1.add(soba);
							}
						}else {
							if(soba.isBalkon()) {
								trazeneSobe1.add(soba);
							}
						}
					}else {
						if(tv) {
							if(soba.isTv()) {
								trazeneSobe1.add(soba);
							}
						}else {
								trazeneSobe1.add(soba);
							
						}
					}
				}
			}
		}

		ArrayList<LocalDate> trazeniDatumi = new ArrayList<LocalDate>();
		LocalDate datum = pocetniDatum;
		while(true) {
			trazeniDatumi.add(datum);
			if(datum.equals(krajnjiDatum)) {
				break;
			}
			datum = datum.plusDays(1);
		}
		for(Soba soba: trazeneSobe1) {
//			System.out.println(soba);
			ArrayList<LocalDate> sobaDatumi = soba.getSlobodniDatumi();
			boolean tf = true;
			for(LocalDate date: trazeniDatumi) {
				if(!sobaDatumi.contains(date)) {
					tf = false;
					break;
				}
			}
			if(tf) {
				trazeneSobe.add(soba);
			}
		}
		
		return trazeneSobe.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ArrayList<Soba> trazeneSobe1 = new ArrayList<Soba>();
		ArrayList<Soba> trazeneSobe = new ArrayList<Soba>();
		for(Soba soba: sobeManager.getSobe()) {
			
			if(soba.getTipSobe().equals(tipSobe)) {
				if(klima) {
					if(balkon) {
						if(tv) {
							if(soba.isKlima() & soba.isBalkon() & soba.isTv()) {
								trazeneSobe1.add(soba);
							}
						}else {
							if(soba.isKlima() & soba.isBalkon()) {
								trazeneSobe1.add(soba);
							}
						}
					}else {
						if(tv) {
							if(soba.isKlima()  & soba.isTv()) {
								trazeneSobe1.add(soba);
							}
						}else {
							if(soba.isKlima() ) {
								trazeneSobe1.add(soba);
							}
						}
					}
				}else {
					if(balkon) {
						if(tv) {
							if( soba.isBalkon() & soba.isTv()) {
								trazeneSobe1.add(soba);
							}
						}else {
							if(soba.isBalkon()) {
								trazeneSobe1.add(soba);
							}
						}
					}else {
						if(tv) {
							if(soba.isTv()) {
								trazeneSobe1.add(soba);
							}
						}else {
								trazeneSobe1.add(soba);
							
						}
					}
				}
			}
		}

		ArrayList<LocalDate> trazeniDatumi = new ArrayList<LocalDate>();
		LocalDate datum = pocetniDatum;
		while(true) {
			trazeniDatumi.add(datum);
			if(datum.equals(krajnjiDatum)) {
				break;
			}
			datum = datum.plusDays(1);
		}
		for(Soba soba: trazeneSobe1) {
//			System.out.println(soba);
			ArrayList<LocalDate> sobaDatumi = soba.getSlobodniDatumi();
			boolean tf = true;
			for(LocalDate date: trazeniDatumi) {
				if(!sobaDatumi.contains(date)) {
					tf = false;
					break;
				}
			}
			if(tf) {
				trazeneSobe.add(soba);
			}
		}
		
		if(trazeneSobe.size() != 0) {
			Soba soba = trazeneSobe.get(rowIndex);
			
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
