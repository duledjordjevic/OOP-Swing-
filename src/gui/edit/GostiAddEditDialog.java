package gui.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import gui.GostiTableFrame;
import korisnici.Gost;
import korisnici.Korisnik;
import managers.CenovniciManager;
import managers.KorisniciManager;
import managers.RezervacijeManager;
import managers.SobeManager;
import net.miginfocom.swing.MigLayout;


public class GostiAddEditDialog extends JDialog{
	private KorisniciManager korisniciManager;
	private SobeManager sobeManager;
	private RezervacijeManager rezervacijeManager;
	private CenovniciManager cenovniciManager;
	private Gost editGost;
	private JFrame parent;
	
	// Jedan isti dijalog za Add i Edit
	public GostiAddEditDialog(JFrame parent, KorisniciManager korisniciManager, Gost editGost, 
			SobeManager sobeManager, RezervacijeManager rezervacijeManager, CenovniciManager cenovniciManager) {
		super(parent, true);
		this.parent = parent;
		if (editGost != null) {
			setTitle("Izmena gostiju");
		} else {
			setTitle("Dodavanje gostiju");
		}
		this.sobeManager = sobeManager;
		this.rezervacijeManager = rezervacijeManager;
		this.cenovniciManager = cenovniciManager;
		this.korisniciManager = korisniciManager;
		this.editGost = editGost;

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

//		JLabel lblId = new JLabel("Id");
//		add(lblId);

//		JTextField txtId = new JTextField(20);
//		add(txtId, "span 2");
//		JLabel lblTipKorisnika = new JLabel("Tip korisnika");
//		add(lblTipKorisnika);
//
//		String[] tipKorisnika = {"recepcioner", "sobarica"};
//		JComboBox combobox1 = new JComboBox(tipKorisnika);
//		add(combobox1, "span 2");

		JLabel lblIme = new JLabel("Ime");
		add(lblIme);

		JTextField txtIme = new JTextField(20);
		add(txtIme, "span 2");

		JLabel lblPrezime = new JLabel("Prezime");
		add(lblPrezime);

		JTextField txtPrezime = new JTextField(20);
		add(txtPrezime, "span 2");

		
		JLabel lblPol = new JLabel("Pol");
		add(lblPol);

		String[] pol = {"muski", "zenski"};
		JComboBox combobox = new JComboBox(pol);
		add(combobox, "span 2");

		JLabel lblDatumRodjenja = new JLabel("Datum rodjenja");
		add(lblDatumRodjenja);

//		JCalendar calendar = new JCalendar();
		JDateChooser chooser = new JDateChooser();
		chooser.setDateFormatString("dd.MM.yyyy");
//		JTextField txtDatumRodjenja = new JTextField(20);
		add(chooser, "span 2");
		
		JLabel lblTelefon = new JLabel("Telefon");
		add(lblTelefon);

		JTextField txtTelefon = new JTextField(20);
		add(txtTelefon, "span 2");
		
		JLabel lblAdresa= new JLabel("Adresa");
		add(lblAdresa);

		JTextField txtAdresa = new JTextField(20);
		add(txtAdresa, "span 2");
		
		JLabel lblUsername = new JLabel("Email");
		add(lblUsername);

		JTextField txtUsername = new JTextField(20);
		add(txtUsername, "span 2");
		
		
		JLabel lblLozinka = new JLabel("Broj pasosa");
		add(lblLozinka);

		JTextField txtLozinka = new JTextField(20);
		add(txtLozinka, "span 2");
		
//		JLabel lblNivoStrucneSpreme = new JLabel("NivoStrucneSpreme");
//		add(lblNivoStrucneSpreme);
//
//		JTextField txtNivoStrucneSpreme = new JTextField(20);
//		add(txtNivoStrucneSpreme, "span 2");
//		
//		JLabel lblStaz = new JLabel("Staz");
//		add(lblStaz);
//
//		JTextField txtStaz = new JTextField(20);
//		add(txtStaz, "span 2");
		
		// prazno za prvu kolonu
		add(new JLabel());

		JButton btnCancel = new JButton("Cancel");
		add(btnCancel);

		JButton btnOK = new JButton("OK");
		add(btnOK);

		if(editGost != null) {
	//		// popuni polja vrednostima
			
			txtIme.setText(editGost.getIme());
			txtPrezime.setText(editGost.getPrezime());
			
	//		if(korisniciManager.getRecepcioneri().containsKey(editKorisnik.getUsername())) {
	//			combobox1.setSelectedIndex(0);
	//		}else {
	//			combobox1.setSelectedIndex(1);
	//		}
			
			if(editGost.getPol().equals("muski")) {
				combobox.setSelectedIndex(0);
			}else {
				combobox.setSelectedIndex(1);
			}
			
			java.util.Date datumRodjenja = null;
			try {
				datumRodjenja = new SimpleDateFormat("dd.MM.yyyy").parse(editGost.getDatum());
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			chooser.setDate(datumRodjenja);
			
			txtTelefon.setText(editGost.getTelefon());
			txtAdresa.setText(editGost.getAdresa());
			
			txtUsername.setText(editGost.getUsername());
			txtLozinka.setText(editGost.getLozinka());
			
//			String s1 = String.valueOf(editGost.getNivoStrucneSpreme());
//			txtNivoStrucneSpreme.setText(s1);
//			
//			String s2 = String.valueOf(editGost.getStaz());
//			txtStaz.setText(s2);
	
		}
		
	        
	    

		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (editGost != null) {
					if (chooser.getDate() != null) {
						String ime = txtIme.getText().trim();
						
						String prezime = txtPrezime.getText().trim();
						String pol = combobox.getSelectedItem().toString();
						
						java.util.Date datum = chooser.getDate();
						DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");  
						String datumRodjenja = dateFormat.format(datum);
						
						String telefon = txtTelefon.getText().trim();
						String adresa = txtAdresa.getText().trim();
						String username = txtUsername.getText().trim();
						String lozinka = txtLozinka.getText().trim();
						
						
						// odve se odvaja GUI od funkcionalnosti Manager-a
						// ne mesati logiku app i funkcionalnosti sa GUI-om !
						
						if(!ime.isEmpty() &  !prezime.isEmpty()  & !telefon.isEmpty() & !adresa.isEmpty() & !username.isEmpty() & !lozinka.isEmpty() ) {
							String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
		                            "[a-zA-Z0-9_+&*-]+)*@" +
		                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
		                            "A-Z]{2,7}$";
		                              
					        Pattern pat = Pattern.compile(emailRegex);
					        if (pat.matcher(username).matches()) {
					        	boolean numeric, numeric1;
						        numeric = txtTelefon.getText().trim().matches("-?\\d+(\\.\\d+)?");
						        numeric1 = txtLozinka.getText().trim().matches("-?\\d+(\\.\\d+)?");
						        if(numeric & numeric1) {
						        	
						        	numeric = ime.matches(".*[0-9].*");
						        	numeric1 = prezime.matches(".*[0-9].*");
						        	if (!numeric & !numeric1) {
						        		
										boolean tf = true;
										for(Korisnik k: korisniciManager.getKorisnici()) {
											if(username.equals(k.getUsername())) {
												
												if(!username.equals(editGost.getUsername())) {
													tf = false;
												}
												
											}
										}
										if(tf) {
											korisniciManager.editGost(editGost.getId(), ime, prezime, pol, datumRodjenja, telefon, adresa, username, lozinka);
											((GostiTableFrame) parent).refreshData();
											GostiAddEditDialog.this.dispose();
										}else {
											JOptionPane.showMessageDialog(null, "To korisnicko ime je vec zauzeto", "Greska", JOptionPane.WARNING_MESSAGE);
										}
						        	}else {
						        		JOptionPane.showMessageDialog(null, "Ime i prezime ne mogu sadrzati brojeve", "Greska", JOptionPane.WARNING_MESSAGE);
						        	}
						        	
								}else {
									JOptionPane.showMessageDialog(null, "Morate uneti broj za telefon i broj pasosa.", "Greska", JOptionPane.WARNING_MESSAGE);
								}
					        }else {
					        	JOptionPane.showMessageDialog(null, "Email mora biti u dobrom formatu.", "Greska", JOptionPane.WARNING_MESSAGE);
					        }
					           
							
							
						
						}else {
							JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
						}
					}else {
						
						JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
					}
					
				}else {

					if (chooser.getDate() != null) {
						String ime = txtIme.getText().trim();
						
						String prezime = txtPrezime.getText().trim();
						String pol = combobox.getSelectedItem().toString();
						
						java.util.Date datum = chooser.getDate();
						DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");  
						String datumRodjenja = dateFormat.format(datum);
						
						String telefon = txtTelefon.getText().trim();
						String adresa = txtAdresa.getText().trim();
						String username = txtUsername.getText().trim();
						String lozinka = txtLozinka.getText().trim();
						
						
						// odve se odvaja GUI od funkcionalnosti Manager-a
						// ne mesati logiku app i funkcionalnosti sa GUI-om !
						
						if(!ime.isEmpty() &  !prezime.isEmpty()  & !telefon.isEmpty() & !adresa.isEmpty() & !username.isEmpty() & !lozinka.isEmpty() ) {
							String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
		                            "[a-zA-Z0-9_+&*-]+)*@" +
		                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
		                            "A-Z]{2,7}$";
		                              
					        Pattern pat = Pattern.compile(emailRegex);
					        if (pat.matcher(username).matches()) {
					        	boolean numeric, numeric1;
						        numeric = txtTelefon.getText().trim().matches("-?\\d+(\\.\\d+)?");
						        numeric1 = txtLozinka.getText().trim().matches("-?\\d+(\\.\\d+)?");
						        if(numeric & numeric1) {

						        	numeric = ime.matches(".*[0-9].*");
						        	numeric1 = prezime.matches(".*[0-9].*");
						        	if (!numeric & !numeric1) {
						        		
										boolean tf = true;
										for(Korisnik k: korisniciManager.getKorisnici()) {
											
											if(username.equals(k.getUsername())) {
												tf = false;
											}
										}
										if(tf) {
											korisniciManager.addGost(ime, prezime, pol, datumRodjenja, telefon, adresa, username, lozinka);
											((GostiTableFrame) parent).refreshData();
											GostiAddEditDialog.this.dispose();
										}else {
											JOptionPane.showMessageDialog(null, "To korisnicko ime je vec zauzeto", "Greska", JOptionPane.WARNING_MESSAGE);
										}
						        	}else {
						        		JOptionPane.showMessageDialog(null, "Ime i prezime ne mogu sadrzati brojeve", "Greska", JOptionPane.WARNING_MESSAGE);
						        	}
						        	
								}else {
									JOptionPane.showMessageDialog(null, "Morate uneti broj za telefon i broj pasosa.", "Greska", JOptionPane.WARNING_MESSAGE);
								}
					        }else {
					        	JOptionPane.showMessageDialog(null, "Email mora biti u dobrom formatu.", "Greska", JOptionPane.WARNING_MESSAGE);
					        }
							
						
						}else {
							JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
						}
					}else {
						
						JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
					}
				}
				
				
			}
		}
		);
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GostiAddEditDialog.this.dispose();
			}
		});
	}
}
