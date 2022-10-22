package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import entity.StatusRezervacije;
import korisnici.Gost;
import managers.CenovniciManager;
import managers.RezervacijeManager;
import model.GostRezervacijeModel;

public class GostRezervacijeTableFrame extends JFrame{
	
	private RezervacijeManager rezervacijeManager;
	private CenovniciManager cenovniciManager;
	private Gost gost;
	
	protected JToolBar mainToolbar = new JToolBar();
//	protected JButton btnAdd = new JButton();
//	protected JButton btnEdit = new JButton();
//	protected JButton btnDelete = new JButton();
	protected JButton btnCancel = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame; 

	public GostRezervacijeTableFrame(JFrame parent, RezervacijeManager rezervacijeManager, CenovniciManager cenovniciManager, Gost gost) {
	
		this.rezervacijeManager = rezervacijeManager;
		this.cenovniciManager = cenovniciManager;
		this.gost = gost;
		
		this.parentFrame = parent;	
		// podesavanje prozora
		setTitle("Moje rezervacije");	
		setSize(1000, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("img/icon.png").getImage());
		
		// podesavanje toolbar-a

		ImageIcon cancelIcon = new ImageIcon("src/img/remove.gif");
		btnCancel.setIcon(cancelIcon);
		mainToolbar.add(btnCancel);
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		
		table = new JTable(new GostRezervacijeModel(rezervacijeManager, gost));		
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
		int ukupnaCena = 0;
		for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
			if(rez.getUsername().equals(gost.getUsername()) & !(rez.getStatusRezervacije().equals(StatusRezervacije.ODBIJENA))) {
				ukupnaCena += rez.getCena();
			}
		}
		// podesavanje pretrage 
		JLabel lblUkupnaCena = new JLabel();
		lblUkupnaCena.setText("Ukupna cena: " + ukupnaCena);
		JPanel pSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));		
		pSearch.setBackground(new java.awt.Color(166, 209, 230));
		pSearch.add(new JLabel("Search:"));
		pSearch.add(tfSearch);
		pSearch.add(lblUkupnaCena);
		
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

		initActions();
	}
	


	private void initActions() {
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
					Rezervacija rezervacija = rezervacijeManager.pronadjiRezervacijuPoId(id);
					
					if(rezervacija != null) {
						if(rezervacija.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)) {
							int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da otkazete rezervaciju?", 
									"id: " + rezervacija.getId() + " "+rezervacija.getTipSobe() +" - Potvrda otkazivanja", JOptionPane.YES_NO_OPTION);
							if(izbor == JOptionPane.YES_OPTION) {
								rezervacijeManager.otkaziRezervaciju(rezervacija.getId());
								refreshData();
							}
						}else if(rezervacija.getStatusRezervacije().equals(StatusRezervacije.POTVRDJENA)) {
							int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da otkazete potvrdjenu rezervaciju? Ne dobijate povracaj novca.", 
									"id: " + rezervacija.getId() + " "+rezervacija.getTipSobe() +" - Potvrda otkazivanja", JOptionPane.YES_NO_OPTION);
							if(izbor == JOptionPane.YES_OPTION) {
								rezervacijeManager.otkaziRezervaciju(rezervacija.getId());
								refreshData();
							}
						}else {
							JOptionPane.showMessageDialog(null, "Nije moguce otkazati tu rezervaciju.", "Greska", JOptionPane.WARNING_MESSAGE);
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranu rezervaciju!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
			
		});
	}
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		GostRezervacijeModel grm = (GostRezervacijeModel)this.table.getModel();
		
		grm.fireTableDataChanged();
	}
	
	// Pamcenje redosleda sortiranja za svaku kolonu posebno - primer
	@SuppressWarnings("serial")
	private Map<Integer, Integer> sortOrder = new HashMap<Integer, Integer>() {{put(0, 1);put(1, 1);put(2, 1);put(3, 1);}};
}
