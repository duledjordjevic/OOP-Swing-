package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
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

import entity.Soba;
import entity.TipSobe;
import gui.edit.OdabirUslugaDialog;
import korisnici.Gost;
import managers.CenovniciManager;
import managers.RezervacijeManager;
import managers.SobeManager;
import model.SobeZaRezervacijuModel;

public class SobeZaRezervisanjeZaGosta extends JFrame{
	private static final long serialVersionUID = 3829177797763520552L;
	
	private SobeManager sobeManager;
	private Gost gost;
	private RezervacijeManager rezervacijeManager;
	private CenovniciManager cenovniciManager;
	private TipSobe tipSobe;
	private LocalDate pocetniDatum;
	private LocalDate krajnjiDatum;
	private boolean klima, balkon, tv;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnRezervisi = new JButton();
//	protected JButton btnAdd = new JButton();
//	protected JButton btnEdit = new JButton();
////		protected JButton btnDelete = new JButton();
//	protected JButton btnCancel = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame; 

	
	public SobeZaRezervisanjeZaGosta(JFrame parent, Gost gost, SobeManager sobeManager, RezervacijeManager rezervacijeManager, CenovniciManager cenovniciManager, TipSobe tipSobe, LocalDate pocetniDatum,
			LocalDate krajnjiDatum, boolean klima, boolean balkon, boolean tv) {
		
		this.gost = gost;
		this.rezervacijeManager = rezervacijeManager;
		this.cenovniciManager = cenovniciManager;
		this.sobeManager = sobeManager;
		this.tipSobe = tipSobe;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.klima = klima;
		this.balkon = balkon;
		this.tv = tv;
		
		this.parentFrame = parent;	
		// podesavanje prozora
		setTitle("Sobe za rezervisanje");	
		setSize(1000, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("img/icon.png").getImage());
		
		// podesavanje toolbar-a
		
		
		
		ImageIcon rezervisiIcon = new ImageIcon("src/img/checked.png");		
		ImageIcon scaled = new ImageIcon(rezervisiIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		rezervisiIcon = scaled;
		
		btnRezervisi.setIcon(rezervisiIcon);
		mainToolbar.add(btnRezervisi);
	
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		
		table = new JTable(new SobeZaRezervacijuModel(sobeManager ,tipSobe, pocetniDatum, krajnjiDatum, klima, balkon, tv));		
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
//					sort(index);
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

		initActions();
	}


	private void initActions() {
		
//		btnAdd.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				RezervacijaSobeAddEditDialog add = new RezervacijaSobeAddEditDialog(gost, RezervacijaSobeTableFrame.this, rezervacijeManager, cenovniciManager,  null);
//				add.setVisible(true);
//			}
//		});
//		
		btnRezervisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
					Soba soba = sobeManager.pronadjiSobuPoId(id);
					
					OdabirUslugaDialog add = new OdabirUslugaDialog(SobeZaRezervisanjeZaGosta.this, gost, sobeManager,  soba, rezervacijeManager, cenovniciManager, pocetniDatum, krajnjiDatum);
					add.setVisible(true);
					
					
//					if(rezervacija != null) {
//						if(rezervacija.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)) {
//							RezervacijaSobeAddEditDialog add = new RezervacijaSobeAddEditDialog(gost, RezervacijaSobeTableFrame.this, rezervacijeManager,cenovniciManager , rezervacija);
//							add.setVisible(true);
//						}else {
//							JOptionPane.showMessageDialog(null, "Nije moguce izmeniti tu rezervaciju.", "Greska", JOptionPane.WARNING_MESSAGE);
//						}
//						
//					}else {
//						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal!", "Greska", JOptionPane.ERROR_MESSAGE);
//					}
				}
			}
		});
////			btnDelete.addActionListener(new ActionListener() {
////				@Override
////				public void actionPerformed(ActionEvent e) {
////					int red = table.getSelectedRow();
////					if(red == -1) {
////						JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
////					}else {
////						int id = Integer.parseInt(table.getValueAt(red, 0).toString());
////						Rezervacija rezervacija = rezervacijeManager.pronadjiRezervacijuPoId(id);
////						if(rezervacija != null) {
////							int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete rezervaciju?", 
////									"id: " + rezervacija.getId() + " "+rezervacija.getTipSobe() +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
////							if(izbor == JOptionPane.YES_OPTION) {
////								rezervacijeManager.removeRezervaciju(rezervacija.getId());
////								refreshData();
////							}
////						}else {
////							JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranu rezervaciju!", "Greska", JOptionPane.ERROR_MESSAGE);
////						}
////					}
////				}
////			});
//		btnCancel.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int red = table.getSelectedRow();
//				if(red == -1) {
//					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
//				}else {
//					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
//					Rezervacija rezervacija = rezervacijeManager.pronadjiRezervacijuPoId(id);
//					
//					if(rezervacija != null) {
//						if(rezervacija.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)) {
//							int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da otkazete rezervaciju?", 
//									"id: " + rezervacija.getId() + " "+rezervacija.getTipSobe() +" - Potvrda otkazivanja", JOptionPane.YES_NO_OPTION);
//							if(izbor == JOptionPane.YES_OPTION) {
//								rezervacijeManager.otkaziRezervaciju(rezervacija.getId());
//								refreshData();
//							}
//						}else if(rezervacija.getStatusRezervacije().equals(StatusRezervacije.POTVRDJENA)) {
//							int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da otkazete potvrdjenu rezervaciju? Ne dobijate povracaj novca.", 
//									"id: " + rezervacija.getId() + " "+rezervacija.getTipSobe() +" - Potvrda otkazivanja", JOptionPane.YES_NO_OPTION);
//							if(izbor == JOptionPane.YES_OPTION) {
//								rezervacijeManager.otkaziRezervaciju(rezervacija.getId());
//								refreshData();
//							}
//						}else {
//							JOptionPane.showMessageDialog(null, "Nije moguce otkazati tu rezervaciju.", "Greska", JOptionPane.WARNING_MESSAGE);
//						}
//						
//					}else {
//						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranu rezervaciju!", "Greska", JOptionPane.ERROR_MESSAGE);
//					}
//				}
//				
//			}
//			
//		});
	}
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		SobeZaRezervacijuModel srm = (SobeZaRezervacijuModel)this.table.getModel();
		srm.fireTableDataChanged();
	}
	
	// Pamcenje redosleda sortiranja za svaku kolonu posebno - primer
	@SuppressWarnings("serial")
	private Map<Integer, Integer> sortOrder = new HashMap<Integer, Integer>() {{put(0, 1);put(1, 1);put(2, 1);put(3, 1);}};


}
