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

import gui.edit.FiltriranjeSobaZaGostaDialog;
import korisnici.Gost;
import managers.ManagerFactory;

public class GostFrame extends JFrame {

	private static final long serialVersionUID = -324473796672236739L;

	private ManagerFactory managers;
	private Gost gost;
	
	public GostFrame(ManagerFactory managers, Gost gost) {
		this.managers = managers;
		this.gost = gost;
		gostFrame();
	}

	private void gostFrame() {
		this.setTitle("Gost");
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("src/img/hotel-building.png").getImage());

		initMainGUI();
		
	}

	private void initMainGUI() {
		JMenuBar mainMenu = new JMenuBar();

		JMenu rezervacijeMenu = new JMenu("Rezervacije");
		JMenuItem mojeRezervacijeItem = new JMenuItem("Moje rezervacije ");
		JMenuItem dodajRezervacijuItem = new JMenuItem("Naparavi rezervaciju");
		

		rezervacijeMenu.add(mojeRezervacijeItem);
		rezervacijeMenu.add(dodajRezervacijuItem);


		mainMenu.add(rezervacijeMenu);
		
		
		this.setJMenuBar(mainMenu);

		// Klikom na stavke menija otvaraju se odgovarajuce forme za prikaz
		dodajRezervacijuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				FiltriranjeSobaZaGostaDialog add = new FiltriranjeSobaZaGostaDialog(GostFrame.this, gost, managers.getSobeManager(), managers.getRezervacijeManager(), managers.getCenovniciManager());
				add.setVisible(true);

			}
		});
		
		mojeRezervacijeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				GostRezervacijeTableFrame add = new GostRezervacijeTableFrame(GostFrame.this, managers.getRezervacijeManager(), managers.getCenovniciManager(), gost);
				add.setVisible(true);

			}
		});

		
		
		
	}

}
