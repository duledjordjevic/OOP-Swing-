package gui.izvestaji;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
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

import managers.KorisniciManager;
import managers.RezervacijeManager;
import model.BrojSpremljenihSobaSobaricaModel;

public class BrojSpremljenihSobaSobaricaTableFrame extends JFrame{

	private RezervacijeManager rezervacijeManager;
	private KorisniciManager korisniciManager;
	private LocalDate pocetniDatum, krajnjiDatum;
	protected JToolBar mainToolbar = new JToolBar();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame; 

	public BrojSpremljenihSobaSobaricaTableFrame(JFrame parent, RezervacijeManager rezervacijeManager, KorisniciManager korisniciManager,
			LocalDate pocetniDatum, LocalDate krajnjiDatum) {
		
		this.rezervacijeManager = rezervacijeManager;
		this.korisniciManager = korisniciManager;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.parentFrame = parent;	
		// podesavanje prozora
		setTitle("Sobarice izvestaji");	
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("img/icon.png").getImage());
		
		// podesavanje toolbar-a

		
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		table = new JTable(new BrojSpremljenihSobaSobaricaModel(rezervacijeManager, korisniciManager, pocetniDatum, krajnjiDatum));		
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
//				sort(index);
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

//		initActions();
	}
	

	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		BrojSpremljenihSobaSobaricaModel sm = (BrojSpremljenihSobaSobaricaModel)this.table.getModel();
		sm.fireTableDataChanged();
	}
	
	// Pamcenje redosleda sortiranja za svaku kolonu posebno - primer
	@SuppressWarnings("serial")
	private Map<Integer, Integer> sortOrder = new HashMap<Integer, Integer>() {{put(0, 1);put(1, 1);put(2, 1);put(3, 1);}};
}
