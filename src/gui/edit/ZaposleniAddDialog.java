package gui.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import gui.ZaposleniTableFrame;
import korisnici.Korisnik;
import korisnici.Zaposleni;
import managers.KorisniciManager;
import net.miginfocom.swing.MigLayout;

public class ZaposleniAddDialog extends JDialog {
	private static final long serialVersionUID = -5247231764310200252L;
	private KorisniciManager korisniciManager;
	private Zaposleni editKorisnik;
	private JFrame parent;
	
	// Jedan isti dijalog za Add i Edit
	public ZaposleniAddDialog(JFrame parent, KorisniciManager korisniciManager, Zaposleni editKorisnik) {
		super(parent, true);
		this.parent = parent;
		if (editKorisnik != null) {
			setTitle("Izmena zaposlenih");
		} else {
			setTitle("Dodavanje zaposlenih");
		}
		this.korisniciManager = korisniciManager;
		this.editKorisnik = editKorisnik;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		// 1. nacin sa MigLayout - dosta laksi
		MigLayout ml = new MigLayout("wrap 3", "[][]", "[]10[]10[]10[]10[]10[]10[]10[]10[]10[]20[]");
		setLayout(ml);

//		JLabel lblId = new JLabel("Id");
//		add(lblId);

//		JTextField txtId = new JTextField(20);
//		add(txtId, "span 2");
		JLabel lblTipKorisnika = new JLabel("Tip korisnika");
		add(lblTipKorisnika);

		String[] tipKorisnika = {"recepcioner", "sobarica"};
		JComboBox combobox1 = new JComboBox(tipKorisnika);
		add(combobox1, "span 2");

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
		
		JLabel lblUsername = new JLabel("Korisnicko ime");
		add(lblUsername);

		JTextField txtUsername = new JTextField(20);
		add(txtUsername, "span 2");
		
		
		JLabel lblLozinka = new JLabel("Lozinka");
		add(lblLozinka);

		JTextField txtLozinka = new JTextField(20);
		add(txtLozinka, "span 2");
		
		JLabel lblNivoStrucneSpreme = new JLabel("NivoStrucneSpreme");
		add(lblNivoStrucneSpreme);

		JTextField txtNivoStrucneSpreme = new JTextField(20);
		add(txtNivoStrucneSpreme, "span 2");
		
		JLabel lblStaz = new JLabel("Staz");
		add(lblStaz);

		JTextField txtStaz = new JTextField(20);
		add(txtStaz, "span 2");
		
		// prazno za prvu kolonu
		add(new JLabel());

		JButton btnCancel = new JButton("Cancel");
		add(btnCancel);

		JButton btnOK = new JButton("OK");
		add(btnOK);

		
//
	

		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String tipKorisnika = combobox1.getSelectedItem().toString();

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
					
					if(!ime.isEmpty() &  !prezime.isEmpty()  & !telefon.isEmpty() & !adresa.isEmpty() & !username.isEmpty() & !lozinka.isEmpty() & !txtNivoStrucneSpreme.getText().isEmpty() & !txtStaz.getText().trim().isEmpty()) {
						
						boolean numeric = true;
						boolean numeric1 = true;
						boolean numeric2 = true;
				        numeric = txtNivoStrucneSpreme.getText().trim().matches("-?\\d+(\\.\\d+)?");
				        numeric1 = txtStaz.getText().trim().matches("-?\\d+(\\.\\d+)?");
				        numeric2 = txtTelefon.getText().trim().matches("-?\\d+(\\.\\d+)?");
				        if(numeric & numeric1 & numeric2) {

				        	numeric = ime.matches(".*[0-9].*");
				        	numeric1 = prezime.matches(".*[0-9].*");
				        	if (!numeric & !numeric1) {
				        		int nivoStrucneSpreme = Integer.valueOf(txtNivoStrucneSpreme.getText().trim());
								int staz = Integer.valueOf(txtStaz.getText().trim());
								System.out.println("dsjddkj");
								boolean tf = true;
								for(Korisnik k: korisniciManager.getKorisnici()) {
									if(username.equals(k.getUsername())) {
										tf = false;
									}
								}
								if(tf) {
									korisniciManager.addZaposleni(tipKorisnika, ime, prezime, pol, datumRodjenja, telefon, adresa, username, lozinka, nivoStrucneSpreme, staz);
									((ZaposleniTableFrame) parent).refreshData();
									ZaposleniAddDialog.this.dispose();
								}else {
									JOptionPane.showMessageDialog(null, "To korisnicko ime je vec zauzeto", "Greska", JOptionPane.WARNING_MESSAGE);
								}
				        	}else {
				        		JOptionPane.showMessageDialog(null, "Ime i prezime ne mogu sadrzati brojeve", "Greska", JOptionPane.WARNING_MESSAGE);
				        	}
				        	
						}else {
							JOptionPane.showMessageDialog(null, "Morate uneti broj za nivo strucne spreme, staz i telefon.", "Greska", JOptionPane.WARNING_MESSAGE);
						}
					
					}else {
						JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					
					JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ZaposleniAddDialog.this.dispose();
			}
		});
	}

}
