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
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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

import gui.edit.FiltriranjeSobaZaGostaDialog;
import gui.edit.GostiAddEditDialog;
import korisnici.Gost;
import korisnici.Korisnik;
import korisnici.Zaposleni;
import managers.CenovniciManager;
import managers.KorisniciManager;
import managers.RezervacijeManager;
import managers.SobeManager;
import model.GostiModel;

public class GostiTableFrame extends JFrame{
	private static final long serialVersionUID = -7696585243495230427L;

	private KorisniciManager korisniciManager;
	private SobeManager sobeManager;
	private RezervacijeManager rezervacijeManager;
	private CenovniciManager cenovniciManager;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnAdd = new JButton();
	protected JButton btnEdit = new JButton();
	protected JButton btnDelete = new JButton();
	protected JButton btnRezervisi = new JButton("Rezervisi");
	
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame; 

	public GostiTableFrame(JFrame parent, KorisniciManager korisniciManager, SobeManager sobeManager,
			RezervacijeManager rezervacijeManager, CenovniciManager cenovniciManager) {
		
		this.korisniciManager = korisniciManager;
		this.sobeManager = sobeManager;
		this.rezervacijeManager = rezervacijeManager;
		this.cenovniciManager = cenovniciManager;
		
		this.parentFrame = parent;	
		// podesavanje prozora
		setTitle("Gosti");	
		setSize(1000, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("img/icon.png").getImage());
		
		// podesavanje toolbar-a
		ImageIcon addIcon = new ImageIcon("src/img/add.png");		
		ImageIcon scaled = new ImageIcon(addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		addIcon = scaled;
		btnAdd.setIcon(addIcon);
		mainToolbar.add(btnAdd);
		ImageIcon editIcon = new ImageIcon("src/img/edit.gif");
		btnEdit.setIcon(editIcon);
		mainToolbar.add(btnEdit);
		ImageIcon deleteIcon = new ImageIcon("src/img/remove.png");
		ImageIcon scaled1 = new ImageIcon(deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		deleteIcon = scaled1;
		btnDelete.setIcon(deleteIcon);
		mainToolbar.add(btnDelete);
		
		mainToolbar.add(btnRezervisi);
		
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		table = new JTable(new GostiModel(korisniciManager));		
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

		initActions();
	}
	

	private void initActions() {
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GostiAddEditDialog add = new GostiAddEditDialog(GostiTableFrame.this, korisniciManager, null, sobeManager, rezervacijeManager, cenovniciManager);
				add.setVisible(true);
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
					Gost s = (Gost) korisniciManager.pronadjiKorisnikaPoId(id);
					if(s != null) {
						GostiAddEditDialog add = new GostiAddEditDialog(GostiTableFrame.this, korisniciManager, s, sobeManager, rezervacijeManager, cenovniciManager);
						add.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
					Korisnik s = korisniciManager.pronadjiKorisnikaPoId(id);
					if(s != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete gosta?", 
								s.getIme() + " "+s.getPrezime() +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							korisniciManager.removeGost(s.getId());
							refreshData();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog korisnika!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnRezervisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
					Gost gost = (Gost) korisniciManager.pronadjiKorisnikaPoId(id);
					if(gost != null) {
						FiltriranjeSobaZaGostaDialog add = new FiltriranjeSobaZaGostaDialog(parentFrame, gost, sobeManager, rezervacijeManager, cenovniciManager);
						add.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
//		int izbor = JOptionPane.showConfirmDialog(null,"Da li ste zelite da rezerviste neku sobu?", 
//				"", JOptionPane.YES_NO_OPTION);
//		if(izbor == JOptionPane.YES_OPTION) {
//			Gost gost = korisniciManager.getGosti().get(korisniciManager.getGosti().size() - 1);
//			FiltriranjeSobaZaGostaDialog add = new FiltriranjeSobaZaGostaDialog(parent, gost, sobeManager, rezervacijeManager, cenovniciManager);
//			add.setVisible(true);
//		}
	}
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		GostiModel gm = (GostiModel)this.table.getModel();
		gm.fireTableDataChanged();
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
		put(7, 1);
		put(8, 1);
		}};

	// Manuelni sorter - potrebno za razumevanje rada podrazumevanog sortera tabele
	protected void sort(int index) {
		// index of table column
		
		this.korisniciManager.getZaposleni().sort(new Comparator<Zaposleni>() {
			int retVal = 0;
			@Override
			public int compare(Zaposleni o1, Zaposleni o2) {
				switch (index) {
				case 0:
					retVal = ((Integer)o1.getId()).compareTo((Integer)o2.getId());
					break;
				case 1:
					retVal = o1.getIme().compareTo(o2.getIme());
					break;
				case 2:
					retVal = o1.getPrezime().compareTo(o2.getPrezime());
					break;
				case 3:
					retVal = o1.getPol().compareTo(o2.getPol());
					break;
				case 4:
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					 LocalDate datum1 = LocalDate.parse(o1.getDatum(), formatter);
					 LocalDate datum2 = LocalDate.parse(o2.getDatum(), formatter);
					retVal = datum1.compareTo(datum2);
					break;
				case 5:
					retVal = o1.getTelefon().compareTo(o2.getTelefon());
					break;
				case 6:
					retVal = o1.getAdresa().compareTo(o2.getAdresa());
					break;
				case 7:
					retVal = o1.getUsername().compareTo(o2.getUsername());
					break;
				case 8:
					retVal = o1.getLozinka().compareTo(o2.getLozinka());
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

