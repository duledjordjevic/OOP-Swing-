package model;

import javax.swing.table.AbstractTableModel;

import entity.Soba;
import managers.SobeManager;

public class SobeModel extends AbstractTableModel{

	private SobeManager sobeManager;
//	#id; tip sobe; status sobe; slobodni datumi
	private String[] columnNames = { "Id", "Tip sobe",  "Status sobe", "Klima" , "Balkon", "Tv"};

	public SobeModel(SobeManager sobeManager) {
		this.sobeManager = sobeManager;
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
		
		if(sobeManager.getSobe().size() != 0) {
			Soba soba = sobeManager.getSobe().get(rowIndex);
			
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
