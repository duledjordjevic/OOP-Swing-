package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import entity.Rezervacija;
import managers.RezervacijeManager;
import model.SveRezervacijeModel;

public class SveRezervacijeTableFrame extends JFrame{

	private RezervacijeManager rezervacijeManager;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame; 

	public SveRezervacijeTableFrame(JFrame parent, RezervacijeManager rezervacijeManager) {
	
		this.rezervacijeManager = rezervacijeManager;

		this.parentFrame = parent;	
		// podesavanje prozora
		setTitle("Sve rezervacije");	
		setSize(1200, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("img/icon.png").getImage());
		
		// podesavanje toolbar-a

		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		
		table = new JTable(new SveRezervacijeModel(rezervacijeManager));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		// podesavanje manuelnog sortera tabele, potrebno i za pretragu
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);
		
		
		
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// preuzimanje indeksa kolone potrebnog za sortiranje
				int index = table.getTableHeader().columnAtPoint(arg0.getPoint());
				
				// call abstract sort method
				sort(index);
			}
			
		});
		
		// podesavanje pretrage 
		JPanel pSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));		
		pSearch.setBackground(new java.awt.Color(166, 209, 230));
		pSearch.add(new JLabel("Search:"));
		pSearch.add(tfSearch);
		
		add(pSearch, BorderLayout.SOUTH);
		
		tfSearch.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				//System.out.println("~ "+tfSearch.getText());
				if (tfSearch.getText().trim().length() == 0) {
				     tableSorter.setRowFilter(null);
				  } else {
					  tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + tfSearch.getText().trim()));
				  }
			}
		});

		
	}
	

	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		SveRezervacijeModel srm = (SveRezervacijeModel)this.table.getModel();
		srm.fireTableDataChanged();
	}
	
	// Pamcenje redosleda sortiranja za svaku kolonu posebno - primer
	@SuppressWarnings("serial")
	private Map<Integer, Integer> sortOrder = new HashMap<Integer, Integer>() {
		{put(0, 1);
		put(1, 1);
		put(2, 1);
		put(3, 1);
		put(4, 1);
		put(5, 1);
		put(6, 1);

		}};

	// Manuelni sorter - potrebno za razumevanje rada podrazumevanog sortera tabele
	protected void sort(int index) {
		// index of table column
		
		this.rezervacijeManager.getRezervacije().sort(new Comparator<Rezervacija>() {
			int retVal = 0;
			@Override
			public int compare(Rezervacija o1, Rezervacija o2) {
				switch (index) {
				case 0:
					retVal = ((Integer)o1.getId()).compareTo((Integer)o2.getId());
					break;
				case 1:
					retVal = o1.getTipSobe().compareTo(o2.getTipSobe());
					break;
				case 2:
					retVal = o1.getPocetniDatum().compareTo(o2.getPocetniDatum());
					break;
				case 3:
					retVal = o1.getKrajnjiDatum().compareTo(o2.getKrajnjiDatum());
					break;
				case 4:
					retVal = o1.getStatusRezervacije().compareTo(o2.getStatusRezervacije());
					break;
				case 5:
					retVal = Integer.valueOf(o1.getCena()).compareTo(Integer.valueOf(o2.getCena()));
					break;
				default:
					break;
				}
				return retVal*sortOrder.get(index);
			}
		});
		
//		System.out.println("kolona "+index+" smer "+sortOrder.get(index));
		sortOrder.put(index, sortOrder.get(index)*-1);
		refreshData();	
		
	}
}
