package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
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
import managers.RezervacijeManager;
import managers.SobeManager;
import model.CheckInModel;
import model.PotvrdjivanjeRezervacijaModel;

public class PotvrdjivanjeRezervacijaTableFrame extends JFrame{
	private static final long serialVersionUID = 3577463859580937630L;
	
	private RezervacijeManager rezervacijeManager;
	private SobeManager sobeManager;
	
	protected JToolBar mainToolbar = new JToolBar();

	protected JButton btnChecked = new JButton();
	protected JButton btnCanceled = new JButton();
	
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame; 

	public PotvrdjivanjeRezervacijaTableFrame(JFrame parent, RezervacijeManager rezervacijeManager, SobeManager sobeManager) {
		
		this.rezervacijeManager = rezervacijeManager;
		this.sobeManager = sobeManager;
		
		this.parentFrame = parent;	
		// podesavanje prozora
		setTitle("Potvrdjivanje rezervacija");	
		setSize(1000, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("img/icon.png").getImage());
		
		// podesavanje toolbar-a

		
		ImageIcon checkedIcon = new ImageIcon("src/img/checked.png");
		ImageIcon scaled = new ImageIcon(checkedIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		checkedIcon = scaled;
		btnChecked.setIcon(checkedIcon);
		mainToolbar.add(btnChecked);
		
		ImageIcon canceledIcon = new ImageIcon("src/img/remove.gif");
		btnCanceled.setIcon(canceledIcon);
		mainToolbar.add(btnCanceled);
		
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		table = new JTable(new PotvrdjivanjeRezervacijaModel(rezervacijeManager, sobeManager));		
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

		initActions();
	}
	

	private void initActions() {
		btnChecked.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
					Rezervacija rez = (Rezervacija) rezervacijeManager.pronadjiRezervacijuPoId(id);
					if(rez != null) {
						rezervacijeManager.checkedRezervaciju(id);
						JOptionPane.showMessageDialog(null, "Uspesno ste potvrdili rezervaciju!", "Prihvacena", JOptionPane.INFORMATION_MESSAGE);
						refreshData();
						
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		btnCanceled.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
					Rezervacija rez = (Rezervacija) rezervacijeManager.pronadjiRezervacijuPoId(id);
					if(rez != null) {
						rezervacijeManager.cancelRezervaciju(id);
						JOptionPane.showMessageDialog(null, "Uspesno ste otkazali rezervaciju!", "Prihvacena", JOptionPane.INFORMATION_MESSAGE);
						refreshData();
						
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});


	}
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		PotvrdjivanjeRezervacijaModel prm = (PotvrdjivanjeRezervacijaModel)this.table.getModel();
		prm.fireTableDataChanged();
	}
	
	// Pamcenje redosleda sortiranja za svaku kolonu posebno - primer
	@SuppressWarnings("serial")
	private Map<Integer, Integer> sortOrder = new HashMap<Integer, Integer>() {{put(0, 1);put(1, 1);put(2, 1);put(3, 1);}};
}
