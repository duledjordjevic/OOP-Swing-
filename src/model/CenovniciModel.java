package model;

import java.time.format.DateTimeFormatter;

import javax.swing.table.AbstractTableModel;

import entity.Cene;
import managers.CenovniciManager;

public class CenovniciModel extends AbstractTableModel{
	
	
	private static final long serialVersionUID = 3916108523030136472L;
	private CenovniciManager cenovniciManager;
//	#id; JEDNOKREVETNA ; DVOKREVETNA_SA_JEDNIM_LEZAJEM; DVOKREVETNA_SA_DVA_LEZAJA; TROKREVETNA; dorucak; rucak; vecera; spa centar; pocetni datum; krajnji datum
	private String[] columnNames = { "Id", "JEDNOKREVETNA", "DVOKREVETNA_SA_JEDNIM_LEZAJEM", "DVOKREVETNA_SA_DVA_LEZAJA", "TROKREVETNA", "Dorucak", "Rucak", "Vecera", "Spa centar", "Pocetni datum", "Krajnji datum", "Aktivan"};

	public CenovniciModel(CenovniciManager cenovniciManager) {
		this.cenovniciManager = cenovniciManager;
	}

	@Override
	public int getRowCount() {
		return cenovniciManager.getCenovnici().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(cenovniciManager.getCenovnici().size() != 0) {
			Cene cena = cenovniciManager.getCenovnici().get(rowIndex);
			switch (columnIndex) {
			case 0:
				return cena.getId();
			case 1:
				return cena.getJednokrevetnaSoba();
			case 2:
				return cena.getDvokrevetnaSaJednimLezajemSoba();
			case 3:
				return cena.getDvokrevetnaSaDvaLezajaSoba();
			case 4:
				return cena.getTrokrevetnaSoba();
			case 5:
				return cena.getDorucak();
			case 6:
				return cena.getRucak();
			case 7:
				return cena.getVecera();
			case 8:
				return cena.getSpaCentar();
			case 9:
				String formattedDate = cena.getPocetniDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				return formattedDate;
			case 10:
				formattedDate = cena.getKrajnjiDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				return formattedDate;
			case 11:
				if(cena.isActive()) {
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
