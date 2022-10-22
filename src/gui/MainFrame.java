package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import korisnici.Administrator;
import korisnici.Gost;
import korisnici.Korisnik;
import korisnici.Recepcioner;
import korisnici.Sobarica;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 8456560429229699542L;

	private ManagerFactory managers;
	
	public MainFrame(ManagerFactory managers) {
		this.managers = managers;

		loginDialog();
		
//		mainFrame();
		
	}

	private void loginDialog() {
		JDialog d = new JDialog();
		d.setTitle("Prijava");
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		d.setResizable(false);
		initLoginGUI(d);
		d.pack();
		d.setVisible(true);
	}

	private void initLoginGUI(JDialog dialog) {
		/*
		 * Malo detaljnije podesavanje MigLayout-a: Drugi parametar (string) sadrzi 2
		 * prazne uglaste zagrade jer imamo 2 kolone (ovde nista nismo podesili) Treci
		 * parametar ima onoliko uglastih zagrada koliko imamo redova (u nasem slucaju
		 * 4) Unutar zagrada mozemo detaljnije podesavati kolone i redove, dok vrednosti
		 * izmedju njih predstavljaju razmake u pikselima. Ovde smo postavili razmak od
		 * 20px izmedju 1. i 2. i izmedju 3. i 4. reda.
		 */
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]20[][]20[]");
		dialog.setLayout(layout);

		JTextField tfKorisnickoIme = new JTextField(25);
		JPasswordField pfLozinka = new JPasswordField(25);
		JButton btnOk = new JButton("OK");
		JButton btnCancel = new JButton("Cancel");

		// Ako postavimo dugme 'btnOK' kao defaul button, onda ce svaki pritisak tastera
		// Enter na tastaturi
		// Izazvati klik na njega
		dialog.getRootPane().setDefaultButton(btnOk);

		dialog.add(new JLabel("Dobrodošli. Molimo da se prijavite."), "span 2");
		dialog.add(new JLabel("Korisničko ime"));
		dialog.add(tfKorisnickoIme);
		dialog.add(new JLabel("Šifra"));
		dialog.add(pfLozinka);
		dialog.add(new JLabel());
		dialog.add(btnOk, "split 2");
		dialog.add(btnCancel);

		// Klik na Login dugme
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String korisnickoIme = tfKorisnickoIme.getText().trim();
				String lozinka = new String(pfLozinka.getPassword()).trim();
//				System.out.println(korisnickoIme+" "+lozinka);
				// TO DO
				// Ukoliko nesto nije uneseno, obavestimo korisnika
				// JOptionPane.showMessageDialog(null, "Niste uneli sve podatke.")
				// Provera login podataka, ispisujemo poruku ukoliko korisnik nije nadjen
				// Ukoliko su podaci ispravni, od menadzera korisnika dobijamo objekat korisnika				
				// Sakrijemo Login prozor i dispose-ujemo
				
				ArrayList<Korisnik> korisnici = managers.getKorisniciManager().getKorisnici();
				HashMap<String, Administrator> administratori = managers.getKorisniciManager().getAdministrator();
				HashMap<String, Recepcioner> recepcioneri = managers.getKorisniciManager().getRecepcioneri();
				HashMap<String, Gost> gostiHashMap = managers.getKorisniciManager().getGostiHashMap();
				HashMap<String, Sobarica> sobarice = managers.getKorisniciManager().getSobarice();
				
				
				if(korisnickoIme.length() == 0 || lozinka.length() == 0) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke.");
					
				}else if(administratori.containsKey(korisnickoIme.trim())) {
					if(administratori.get(korisnickoIme).getLozinka().equals(lozinka)){
						
						dialog.setVisible(false);
						dialog.dispose();
						
						AdminFrame adminFrame = new AdminFrame(managers);
						adminFrame.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Niste ispravno uneli podatke.");
					}
				}else if(recepcioneri.containsKey(korisnickoIme.trim())) {
					if(recepcioneri.get(korisnickoIme).getLozinka().equals(lozinka)){
						
						dialog.setVisible(false);
						dialog.dispose();
						
						RecepcionerFrame recepcionerFrame = new RecepcionerFrame(managers);
						recepcionerFrame.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Niste ispravno uneli podatke.");
					}
					
				}else if(gostiHashMap.containsKey(korisnickoIme.trim())) {
					if(gostiHashMap.get(korisnickoIme).getLozinka().equals(lozinka)){
						
						dialog.setVisible(false);
						dialog.dispose();
						Gost gost = gostiHashMap.get(korisnickoIme);
						GostFrame gostFrame = new GostFrame(managers, gost);
						gostFrame.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Niste ispravno uneli podatke.");
					}
					
				}else if(sobarice.containsKey(korisnickoIme.trim())) {
					if(sobarice.get(korisnickoIme).getLozinka().equals(lozinka)){
						
						dialog.setVisible(false);
						dialog.dispose();
						
						Sobarica sobarica = sobarice.get(korisnickoIme);
						SobaricaFrame sobaricaFrame = new SobaricaFrame(managers, sobarica);
						sobaricaFrame.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Niste ispravno uneli podatke.");
					}
				}else{	
					JOptionPane.showMessageDialog(null, "Niste ispravno uneli podatke.");
				}
				
				
				
				
			}
		});
		// Cancel dugme samo sakriva trenutni prozor
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				dialog.dispose();
			}
		});

	}

//	private void mainFrame() {
//		this.setTitle("Hotel");
//		this.setSize(600, 600);
//		this.setLocationRelativeTo(null);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setResizable(false);
//		this.setIconImage(new ImageIcon("src/img/icon.png").getImage());
//
//		initMainGUI();
//
//
//	}
//
//	private void initMainGUI() {
//		JMenuBar mainMenu = new JMenuBar();
//
//		JMenu korisniciMenu = new JMenu("Korisnici");
//		JMenuItem zaposleniiItem = new JMenuItem("Prikaži zaposlene");
//		
//
//		korisniciMenu.add(zaposleniiItem);
//
//
//		mainMenu.add(korisniciMenu);
//		
//		
//		this.setJMenuBar(mainMenu);
//
//		// Klikom na stavke menija otvaraju se odgovarajuce forme za prikaz
//		zaposleniiItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				ZaposleniTableFrame tf = new ZaposleniTableFrame(MainFrame.this, managers.getKorisniciManager());
//				tf.setVisible(true);
//			}
//		});
//
//		
//		// tmp
//		add(new JLabel("  Ovde na centralnom mestu glavnog prozora staviti ono sto je najbitnije za korisnika!"), BorderLayout.CENTER);
//		
//	}
}
