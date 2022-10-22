package gui.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import entity.Cene;
import gui.CenovniciTableFrame;
import managers.CenovniciManager;
import net.miginfocom.swing.MigLayout;

public class CenovniciAddEditDialog extends JDialog{

	private static final long serialVersionUID = -7319448407717963416L;

	private CenovniciManager cenovniciManager;
	private Cene editCena;
	private JFrame parent;
	
	// Jedan isti dijalog za Add i Edit
	public CenovniciAddEditDialog(JFrame parent, CenovniciManager cenovniciManager, Cene editCena) {
		super(parent, true);
		this.parent = parent;
		if (editCena != null) {
			setTitle("Izmena cenovnika");
		} else {
			setTitle("Dodavanje cenovnika");
		}
		this.cenovniciManager = cenovniciManager;
		this.editCena = editCena;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		// 1. nacin sa MigLayout - dosta laksi
		MigLayout ml = new MigLayout("wrap 3", "[][]", "[]10[]10[]10[]10[]10[]10[]20[]");
		setLayout(ml);

		JLabel lblJednokrevetna = new JLabel("Jednokrevetna");
		add(lblJednokrevetna);

		JTextField txtJednokrevetna = new JTextField(20);
		add(txtJednokrevetna, "span 2");
		
		JLabel lblDvokrevetnaSaJednimLezajem = new JLabel("Dvokrevetna sa jednim lezajem");
		add(lblDvokrevetnaSaJednimLezajem);

		JTextField txtDvokrevetnaSaJednimLezajem = new JTextField(20);
		add(txtDvokrevetnaSaJednimLezajem, "span 2");
		
		JLabel lblDvokrevetnaSaDvaLezaja = new JLabel("Dvokrevetna sa dva lezaja");
		add(lblDvokrevetnaSaDvaLezaja);

		JTextField txtDvokrevetnaSaDvaLezaja = new JTextField(20);
		add(txtDvokrevetnaSaDvaLezaja, "span 2");
		
		JLabel lblTrokrevetna = new JLabel("Trokrevetna");
		add(lblTrokrevetna);

		JTextField txtTrokrevetna = new JTextField(20);
		add(txtTrokrevetna, "span 2");
		
		
		JLabel lblDorucak = new JLabel("Dorucak");
		add(lblDorucak);

		JTextField txtDorucak = new JTextField(20);
		add(txtDorucak, "span 2");
		
		JLabel lblRucak = new JLabel("Rucak");
		add(lblRucak);

		JTextField txtRucak = new JTextField(20);
		add(txtRucak, "span 2");
		
		JLabel lblVecera = new JLabel("Vecera");
		add(lblVecera);

		JTextField txtVecera= new JTextField(20);
		add(txtVecera, "span 2");
		
		JLabel lblSpaCentar = new JLabel("Spa centar");
		add(lblSpaCentar);

		JTextField txtSpaCentar= new JTextField(20);
		add(txtSpaCentar, "span 2");
		
		JLabel lblPocetniDatum = new JLabel("Pocetni datum");
		add(lblPocetniDatum);


		JDateChooser chooser1 = new JDateChooser();
		chooser1.setDateFormatString("dd.MM.yyyy");
		add(chooser1, "span 2");
		
		JLabel lblKrajnjiDatum = new JLabel("Krajnji datum");
		add(lblKrajnjiDatum);


		JDateChooser chooser2 = new JDateChooser();
		chooser2.setDateFormatString("dd.MM.yyyy");
		add(chooser2, "span 2");
		
		
		add(new JLabel());

		JButton btnCancel = new JButton("Cancel");
		add(btnCancel);

		JButton btnOK = new JButton("OK");
		add(btnOK);

//		if(editCena != null) {
//			
//			txtJednokrevetna.setText(String.valueOf(editCena.getJednokrevetnaSoba()));
//			txtDvokrevetnaSaJednimLezajem.setText(String.valueOf(editCena.getDvokrevetnaSaJednimLezajemSoba()));
//			txtDvokrevetnaSaDvaLezaja.setText(String.valueOf(editCena.getDvokrevetnaSaDvaLezajaSoba()));
//			txtTrokrevetna.setText(String.valueOf(editCena.getTrokrevetnaSoba()));
//			txtDorucak.setText(String.valueOf(editCena.getDorucak()));
//			txtRucak.setText(String.valueOf(editCena.getRucak()));
//			txtVecera.setText(String.valueOf(editCena.getVecera()));
//			txtSpaCentar.setText(String.valueOf(editCena.getSpaCentar()));
//			
//			ZoneId defaultZoneId = ZoneId.systemDefault();
//			
//			
//		    LocalDate pocetniDatum = editCena.getPocetniDatum();
//		    LocalDate krajnjiDatum = editCena.getKrajnjiDatum();   
//		    
//		    Date date1 = Date.from(pocetniDatum.atStartOfDay(defaultZoneId).toInstant());
//		    Date date2 = Date.from(krajnjiDatum.atStartOfDay(defaultZoneId).toInstant());
//			chooser1.setDate(date1);
//			chooser2.setDate(date2);
//		
//		}

		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(editCena != null) {
//					#edit
//					if (chooser1.getDate() != null & chooser2.getDate() != null & !txtJednokrevetna.getText().isEmpty() & !txtDvokrevetnaSaJednimLezajem.getText().isEmpty() & !txtDvokrevetnaSaDvaLezaja.getText().isEmpty() & !txtTrokrevetna.getText().isEmpty()
//							& !txtDorucak.getText().isEmpty() & !txtRucak.getText().isEmpty() & !txtVecera.getText().isEmpty() & !txtSpaCentar.getText().isEmpty()) {
//						
//						LocalDate pocetniDatum = chooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//						LocalDate krajnjiDatum = chooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;	
//						
//						int jednokrevetna = 0, dvokrevetnaSaJednimLezajem = 0, dvokrevetnaSaDvaLezaja = 0, trokrevetna = 0, dorucak = 0,
//						rucak = 0, vecera = 0, spaCentar = 0;
//						
//						try {
//							jednokrevetna = Integer.parseInt(txtJednokrevetna.getText());
//							dvokrevetnaSaJednimLezajem = Integer.parseInt(txtDvokrevetnaSaJednimLezajem.getText());
//							dvokrevetnaSaDvaLezaja = Integer.parseInt(txtDvokrevetnaSaDvaLezaja.getText());
//							trokrevetna = Integer.parseInt(txtTrokrevetna.getText());
//							dorucak = Integer.parseInt(txtDorucak.getText());
//							rucak = Integer.parseInt(txtRucak.getText());
//							vecera = Integer.parseInt(txtVecera.getText());
//							spaCentar = Integer.parseInt(txtSpaCentar.getText());
//						}catch(NumberFormatException e1) {
//							JOptionPane.showMessageDialog(null, "Morate uneti brojeve za cene.", "Greska", JOptionPane.WARNING_MESSAGE);
//						}
//						
//						
//						Date today = new Date();
//						LocalDate danasnjiDan = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//						
//						if(danasnjiDan.compareTo(pocetniDatum) <= 0 & danasnjiDan.compareTo(krajnjiDatum) <= 0 & pocetniDatum.equals(editCena.getPocetniDatum()) & krajnjiDatum.equals(editCena.getKrajnjiDatum())) {
//							if(pocetniDatum.compareTo(krajnjiDatum) <= 0) {
//								ArrayList<LocalDate> trazeniDatumi = new ArrayList<LocalDate>();
//								while(true) {
//									trazeniDatumi.add(pocetniDatum);
//									pocetniDatum = pocetniDatum.plusDays(1);
//									if(pocetniDatum.equals(krajnjiDatum)) {
//										trazeniDatumi.add(pocetniDatum);
//										break;
//									}
//								}
//								pocetniDatum = chooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//								boolean tf = true;
//								for(Cene cena: cenovniciManager.getCenovnici()) {
//									if(cena.getId() == 1) {
//										continue;
//									}
//									ArrayList<LocalDate> popunjeniDatumi = new ArrayList<LocalDate>();
//									LocalDate datum1 = cena.getPocetniDatum();
//									while(true) {
//										popunjeniDatumi.add(datum1);
//										datum1 = datum1.plusDays(1);
//										if(datum1.equals(cena.getKrajnjiDatum())) {
//											popunjeniDatumi.add(datum1);
//											break;
//										}
//								
//									}
//									for(LocalDate date: popunjeniDatumi) {
//										if(trazeniDatumi.contains(date)) {
//											tf = false;
//										}
//									}
//								}
//								if(tf) {
//									cenovniciManager.editCenovnik(editCena.getId(), jednokrevetna, dvokrevetnaSaJednimLezajem, dvokrevetnaSaDvaLezaja, trokrevetna, dorucak,
//											rucak, vecera, spaCentar, pocetniDatum, krajnjiDatum);
//									((CenovniciTableFrame) parent).refreshData();
//									CenovniciAddEditDialog.this.dispose();
//								}else {
//									JOptionPane.showMessageDialog(null, "Ne mozete napraviti cenovnik u ovom opsegu datuma, jer cenovnik za taj period vec postoji", "Greska", JOptionPane.WARNING_MESSAGE);
//								}
//								
//							}else {
//								JOptionPane.showMessageDialog(null, "Pocetni datum ne moze biti posle krajnjeg", "Greska", JOptionPane.WARNING_MESSAGE);
//							}
//						}else {
//							JOptionPane.showMessageDialog(null, "Ne mozete uneti datume iz proslosti", "Greska", JOptionPane.WARNING_MESSAGE);
//						}
//					}else {
//						JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
//					}
			
					
				}else {
//						#add
					if (chooser1.getDate() != null & chooser2.getDate() != null & !txtJednokrevetna.getText().isEmpty() & !txtDvokrevetnaSaJednimLezajem.getText().isEmpty() & !txtDvokrevetnaSaDvaLezaja.getText().isEmpty() & !txtTrokrevetna.getText().isEmpty()
							& !txtDorucak.getText().isEmpty() & !txtRucak.getText().isEmpty() & !txtVecera.getText().isEmpty() & !txtSpaCentar.getText().isEmpty()) {
						
						LocalDate pocetniDatum = chooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						LocalDate krajnjiDatum = chooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;	
						
						int jednokrevetna = 0, dvokrevetnaSaJednimLezajem = 0, dvokrevetnaSaDvaLezaja = 0, trokrevetna = 0, dorucak = 0,
						rucak = 0, vecera = 0, spaCentar = 0;
						
						try {
							jednokrevetna = Integer.parseInt(txtJednokrevetna.getText());
							dvokrevetnaSaJednimLezajem = Integer.parseInt(txtDvokrevetnaSaJednimLezajem.getText());
							dvokrevetnaSaDvaLezaja = Integer.parseInt(txtDvokrevetnaSaDvaLezaja.getText());
							trokrevetna = Integer.parseInt(txtTrokrevetna.getText());
							dorucak = Integer.parseInt(txtDorucak.getText());
							rucak = Integer.parseInt(txtRucak.getText());
							vecera = Integer.parseInt(txtVecera.getText());
							spaCentar = Integer.parseInt(txtSpaCentar.getText());
						}catch(NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "Morate uneti brojeve za cene.", "Greska", JOptionPane.WARNING_MESSAGE);
						}
						
						
						Date today = new Date();
						LocalDate danasnjiDan = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
						if(danasnjiDan.compareTo(pocetniDatum) <= 0 & danasnjiDan.compareTo(krajnjiDatum) <= 0) {
							if(pocetniDatum.compareTo(krajnjiDatum) <= 0) {
								ArrayList<LocalDate> trazeniDatumi = new ArrayList<LocalDate>();
								while(true) {
									trazeniDatumi.add(pocetniDatum);
									pocetniDatum = pocetniDatum.plusDays(1);
									if(pocetniDatum.equals(krajnjiDatum)) {
										trazeniDatumi.add(pocetniDatum);
										break;
									}
								}
								pocetniDatum = chooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
								boolean tf = true;
								for(Cene cena: cenovniciManager.getCenovnici()) {
									if(cena.isActive()) {
										if(cena.getId() == 1) {
											continue;
										}
										ArrayList<LocalDate> popunjeniDatumi = new ArrayList<LocalDate>();
										LocalDate datum1 = cena.getPocetniDatum();
										while(true) {
											popunjeniDatumi.add(datum1);
											datum1 = datum1.plusDays(1);
											if(datum1.equals(cena.getKrajnjiDatum())) {
												popunjeniDatumi.add(datum1);
												break;
											}
									
										}
										for(LocalDate date: popunjeniDatumi) {
											if(trazeniDatumi.contains(date)) {
												tf = false;
											}
										}
									}
									
								}
								if(tf) {
									cenovniciManager.addCenovnik(jednokrevetna, dvokrevetnaSaJednimLezajem, dvokrevetnaSaDvaLezaja, trokrevetna, dorucak,
											rucak, vecera, spaCentar, pocetniDatum, krajnjiDatum, true);
									((CenovniciTableFrame) parent).refreshData();
									CenovniciAddEditDialog.this.dispose();
								}else {
									JOptionPane.showMessageDialog(null, "Ne mozete napraviti cenovnik u ovom opsegu datuma, jer cenovnik za taj period vec postoji", "Greska", JOptionPane.WARNING_MESSAGE);
								}
								
							}else {
								JOptionPane.showMessageDialog(null, "Pocetni datum ne moze biti posle krajnjeg", "Greska", JOptionPane.WARNING_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(null, "Ne mozete uneti datume iz proslosti", "Greska", JOptionPane.WARNING_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
					}
//				
				}
				
				}
			});		
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CenovniciAddEditDialog.this.dispose();
			}
		});
	}
}
