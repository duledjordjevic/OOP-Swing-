package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import gui.izvestaji.DnevniIzvestaji;
import managers.ManagerFactory;

public class RecepcionerFrame extends JFrame {
	private static final long serialVersionUID = -7420299123399547602L;

	private ManagerFactory managers;
	
	public RecepcionerFrame(ManagerFactory managers) {
		this.managers = managers;
		
		recepcionerFrame();
	}

	private void recepcionerFrame() {
		this.setTitle("Recepcioner");
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("src/img/hotel-building.png").getImage());

		initMainGUI();
		
	}

	private void initMainGUI() {
		JMenuBar mainMenu = new JMenuBar();

		JMenu korisniciMenu = new JMenu("Gosti");
		JMenuItem gostiItem = new JMenuItem("Svi gosti");
		korisniciMenu.add(gostiItem);
		mainMenu.add(korisniciMenu);
		
		JMenu rezervacijeMenu = new JMenu("Rezervacije");
		JMenuItem potvrdjivanjeItem = new JMenuItem("Potvrdjivanje rezervacija");
		JMenuItem checkInItem = new JMenuItem("Check in");
		JMenuItem checkOutItem = new JMenuItem("Check out");
		rezervacijeMenu.add(potvrdjivanjeItem);
		rezervacijeMenu.add(checkInItem);
		rezervacijeMenu.add(checkOutItem);
		mainMenu.add(rezervacijeMenu);
		
		JMenu izvestajiMenu = new JMenu("Izvestaji");
		JMenuItem izvestajiItem = new JMenuItem("Dnevni izvestaji");
		izvestajiMenu.add(izvestajiItem);
		mainMenu.add(izvestajiMenu);
		
		this.setJMenuBar(mainMenu);

		// Klikom na stavke menija otvaraju se odgovarajuce forme za prikaz
		gostiItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				GostiTableFrame tf = new GostiTableFrame(RecepcionerFrame.this, managers.getKorisniciManager(), managers.getSobeManager(), managers.getRezervacijeManager(), managers.getCenovniciManager());
				tf.setVisible(true);
			}
		});
		checkInItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckInTableFrame tf = new CheckInTableFrame(RecepcionerFrame.this, managers.getRezervacijeManager(), managers.getSobeManager());
				tf.setVisible(true);
				
			}
		});
		potvrdjivanjeItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PotvrdjivanjeRezervacijaTableFrame tf = new PotvrdjivanjeRezervacijaTableFrame(RecepcionerFrame.this, managers.getRezervacijeManager(), managers.getSobeManager());
				tf.setVisible(true);
				
			}
		});
		checkOutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckOutTableFrame tf = new CheckOutTableFrame(RecepcionerFrame.this, managers.getRezervacijeManager(), managers.getSobeManager(), managers.getKorisniciManager());
				tf.setVisible(true);
				
			}
		});
		
		izvestajiItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DnevniIzvestaji frame = new DnevniIzvestaji(managers.getRezervacijeManager(), managers.getSobeManager());
				frame.setVisible(true);
				
			}
		});

		
		
		
	}

}
