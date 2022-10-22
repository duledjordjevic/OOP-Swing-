//package gui.edit;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.Date;
//
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JDialog;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JOptionPane;
//
//import com.toedter.calendar.JDateChooser;
//
//import entity.Rezervacija;
//import entity.TipSobe;
//import gui.RezervacijaSobeTableFrame;
//import korisnici.Gost;
//import managers.CenovniciManager;
//import managers.RezervacijeManager;
//import net.miginfocom.swing.MigLayout;
//
//public class RezervacijaSobeAddEditDialog extends JDialog{
//	
//	private RezervacijeManager rezervacijeManager;
//	private CenovniciManager cenovniciManager;
//	private Rezervacija editRezervacija;
//	private JFrame parent;
//	private Gost gost;
//	
//	// Jedan isti dijalog za Add i Edit
//	public RezervacijaSobeAddEditDialog(Gost gost, JFrame parent, RezervacijeManager rezervacijeManager, CenovniciManager cenovniciManager, Rezervacija editRezervacija) {
//		super(parent, true);
//		this.parent = parent;
//		this.gost = gost;
//		if (editRezervacija != null) {
//			setTitle("Izmena rezervacije");
//		} else {
//			setTitle("Dodavanje rezervacija");
//		}
//		this.rezervacijeManager = rezervacijeManager;
//		this.cenovniciManager = cenovniciManager;
//		this.editRezervacija = editRezervacija;
//
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setLocationRelativeTo(null);
//		setResizable(false);
//		initGUI();
//		pack();
//	}
//
//	private void initGUI() {
//		// 1. nacin sa MigLayout - dosta laksi
//		MigLayout ml = new MigLayout("wrap 3", "[][]", "[]10[]10[]10[]10[]20[]");
//		setLayout(ml);
//
//
//		JComboBox combobox = new JComboBox(TipSobe.values());
//		add(combobox, "span 2");
//		add(new JLabel());
//		
//		
//		JLabel lblPocetniDatum = new JLabel("Pocetni datum");
//		add(lblPocetniDatum);
//
////		JCalendar calendar = new JCalendar();
//		JDateChooser chooser1 = new JDateChooser();
//		chooser1.setDateFormatString("dd.MM.yyyy");
////		JTextField txtDatumRodjenja = new JTextField(20);
//		add(chooser1, "span 2");
//		
//		JLabel lblKrajnjiDatum = new JLabel("Krajnji datum");
//		add(lblKrajnjiDatum);
//		
////		JCalendar calendar = new JCalendar();
//		JDateChooser chooser2 = new JDateChooser();
//		chooser2.setDateFormatString("dd.MM.yyyy");
////		JTextField txtDatumRodjenja = new JTextField(20);
//		add(chooser2, "span 2");
//		
//		JLabel lblDodatneUsluge = new JLabel("Dodatne usluge");
//		add(lblDodatneUsluge);
//		
//		String[] dodatneUsluge = {"Klima", "Balkon", "Tv"};
//		JList lista = new JList(dodatneUsluge);
//		add(lista, "span 2");
//
//		JButton btnCancel = new JButton("Cancel");
//		add(btnCancel);
//
//		JButton btnOK = new JButton("OK");
//		add(btnOK);
//
//		if(editRezervacija != null) {
//	//		// popuni polja vrednostima
//			
//			combobox.setSelectedItem(editRezervacija.getTipSobe());
//			
//			
//			ZoneId defaultZoneId = ZoneId.systemDefault();
//			
//			Date pocetniDatum = (Date) Date.from(editRezervacija.getPocetniDatum().atStartOfDay(defaultZoneId).toInstant());
//			System.out.println(editRezervacija.getPocetniDatum());
//			chooser1.setDate(pocetniDatum);
//			Date krajnjiDatum = (Date) Date.from(editRezervacija.getKrajnjiDatum().atStartOfDay(defaultZoneId).toInstant());
//			System.out.println(editRezervacija.getKrajnjiDatum());
//			chooser2.setDate(krajnjiDatum);
//			
//		
//	
//		}
//
//		btnOK.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(editRezervacija != null) {
////					#edit
//					
//					TipSobe tipSobe = TipSobe.valueOf(combobox.getSelectedItem().toString());
//					
//					if (chooser1.getDate() != null & chooser2.getDate() != null) {
//						
//						LocalDate pocetniDatum = chooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//						LocalDate krajnjiDatum = chooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//						Date today = new Date();
//						LocalDate danasnjiDan = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//						
//						if(danasnjiDan.compareTo(pocetniDatum) <= 0 & danasnjiDan.compareTo(krajnjiDatum) <= 0) {
//							if(pocetniDatum.compareTo(krajnjiDatum) <= 0) {
//								rezervacijeManager.editRezervacija(editRezervacija.getId(), tipSobe, pocetniDatum, krajnjiDatum);
//								((RezervacijaSobeTableFrame) parent).refreshData();
//								RezervacijaSobeAddEditDialog.this.dispose();
//							}else {
//								JOptionPane.showMessageDialog(null, "Pocetni datum ne moze biti posle krajnjeg", "Greska", JOptionPane.WARNING_MESSAGE);
//							}
//						}else {
//							JOptionPane.showMessageDialog(null, "Ne mozete uneti datume iz proslosti", "Greska", JOptionPane.WARNING_MESSAGE);
//						}
//					}else {
//						JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
//					}
//				}else {
////					#add
//					TipSobe tipSobe = TipSobe.valueOf(combobox.getSelectedItem().toString());
//					
//					if (chooser1.getDate() != null & chooser2.getDate() != null) {
//						
//						LocalDate pocetniDatum = chooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//						LocalDate krajnjiDatum = chooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//						Date today = new Date();
//						LocalDate danasnjiDan = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//						
//						if(danasnjiDan.compareTo(pocetniDatum) <= 0 & danasnjiDan.compareTo(krajnjiDatum) <= 0) {
//							if(pocetniDatum.compareTo(krajnjiDatum) <= 0) {
//								while(true) {
//									
//								}
//								rezervacijeManager.addRezervacija(gost.getUsername(), tipSobe, pocetniDatum, krajnjiDatum);
//								((RezervacijaSobeTableFrame) parent).refreshData();
//								RezervacijaSobeAddEditDialog.this.dispose();
//							}else {
//								JOptionPane.showMessageDialog(null, "Pocetni datum ne moze biti posle krajnjeg", "Greska", JOptionPane.WARNING_MESSAGE);
//							}
//						}else {
//							JOptionPane.showMessageDialog(null, "Ne mozete uneti datume iz proslosti", "Greska", JOptionPane.WARNING_MESSAGE);
//						}
//					}else {
//						JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
//					}
//				}
//			}
//				
//		});		
//		btnCancel.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				RezervacijaSobeAddEditDialog.this.dispose();
//			}
//		});
//	}
//}
