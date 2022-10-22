package gui;

import java.awt.BorderLayout;
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

import entity.Cene;
import gui.edit.CenovniciAddEditDialog;
import managers.CenovniciManager;
import model.CenovniciModel;

public class CenovniciTableFrame extends JFrame{

	private static final long serialVersionUID = -8609972001403032155L;

	private CenovniciManager cenovniciManager;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton btnAdd = new JButton();
//	protected JButton btnEdit = new JButton();
//	protected JButton btnDelete = new JButton();
	protected JButton btnCancel = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JFrame parentFrame; 

	public CenovniciTableFrame(JFrame parent, CenovniciManager cenovniciManager) {
		
		this.cenovniciManager = cenovniciManager;
		
		this.parentFrame = parent;	
		// podesavanje prozora
		setTitle("Cenovnici");	
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
//		ImageIcon editIcon = new ImageIcon("src/img/edit.gif");
//		btnEdit.setIcon(editIcon);
//		mainToolbar.add(btnEdit);
//		ImageIcon deleteIcon = new ImageIcon("src/img/remove.png");
//		ImageIcon scaled1 = new ImageIcon(deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
//		deleteIcon = scaled1;
//		btnDelete.setIcon(deleteIcon);
//		mainToolbar.add(btnDelete);
		ImageIcon cancelIcon = new ImageIcon("src/img/remove.gif");
		btnCancel.setIcon(cancelIcon);
		mainToolbar.add(btnCancel);
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		table = new JTable(new CenovniciModel(cenovniciManager));		
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
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CenovniciAddEditDialog add = new CenovniciAddEditDialog(CenovniciTableFrame.this, cenovniciManager, null);
				add.setVisible(true);
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
					Cene cena = cenovniciManager.pronadjiCenovnikPoId(id);
					if(!(id == 1) & cena.isActive()) {
//						Cene cena = cenovniciManager.pronadjiCenovnikPoId(id);
						if(cena != null) {
							int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da deaktivirate cenovnik?", 
									"id: " + cena.getId()  +" - Potvrda deaktiviranja", JOptionPane.YES_NO_OPTION);
							if(izbor == JOptionPane.YES_OPTION) {
								cena.setActive(false);
								cenovniciManager.saveData();
								refreshData();
							}
							
						}else {
							JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal!", "Greska", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce deaktivirati ovaj cenovnik", "Greska", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
//		btnEdit.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int red = table.getSelectedRow();
//				if(red == -1) {
//					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
//				}else {
//					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
//					if(!(id == 1)) {
//						Cene cena = cenovniciManager.pronadjiCenovnikPoId(id);
//						if(cena != null) {
//							CenovniciAddEditDialog add = new CenovniciAddEditDialog(CenovniciTableFrame.this, cenovniciManager, cena);
//							add.setVisible(true);
//						}else {
//							JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani artikal!", "Greska", JOptionPane.ERROR_MESSAGE);
//						}
//					}else {
//						JOptionPane.showMessageDialog(null, "Nije moguce izmeniti ovaj cenovnik", "Greska", JOptionPane.ERROR_MESSAGE);
//					}
//					
//				}
//			}
//		});
//		btnDelete.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int red = table.getSelectedRow();
//				if(red == -1) {
//					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
//				}else {
//					int id = Integer.parseInt(table.getValueAt(red, 0).toString());
//					if(!(id == 1)) {
//						Cene s = cenovniciManager.pronadjiCenovnikPoId(id);
//						if(s != null) {
//							int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete cenovnik?", 
//									"id: " + s.getId()  +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
//							if(izbor == JOptionPane.YES_OPTION) {
//								cenovniciManager.removeCenovnik(s.getId());
//								refreshData();
//							}
//						}else {
//							JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog korisnika!", "Greska", JOptionPane.ERROR_MESSAGE);
//						}
//					}else {
//						JOptionPane.showMessageDialog(null, "Nije moguce obrisati ovaj cenovnik", "Greska", JOptionPane.ERROR_MESSAGE);
//					}
//					
//					
//				}
//			}
//		});
	}
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		CenovniciModel sm = (CenovniciModel)this.table.getModel();
		sm.fireTableDataChanged();
	}
	
	// Pamcenje redosleda sortiranja za svaku kolonu posebno - primer
	@SuppressWarnings("serial")
	private Map<Integer, Integer> sortOrder = new HashMap<Integer, Integer>() {{put(0, 1);put(1, 1);put(2, 1);put(3, 1);}};
}
