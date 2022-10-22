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

import korisnici.Sobarica;
import managers.ManagerFactory;

public class SobaricaFrame extends JFrame {

	private static final long serialVersionUID = -1494060140647833553L;

	private ManagerFactory managers;
	private Sobarica sobarica;
	public SobaricaFrame(ManagerFactory managers, Sobarica sobarica) {
		this.managers = managers;
		this.sobarica = sobarica;
		
		sobaricaFrame();
	}

	private void sobaricaFrame() {
		this.setTitle("Sobarica");
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("src/img/hotel-building.png").getImage());

		initMainGUI();
		
	}

	private void initMainGUI() {
		JMenuBar mainMenu = new JMenuBar();

		JMenu sobeMenu = new JMenu("Sobe");
		JMenuItem sobeItem = new JMenuItem("Sobe za sredjivanje");
		

		sobeMenu.add(sobeItem);


		mainMenu.add(sobeMenu);
		
		
		this.setJMenuBar(mainMenu);

		// Klikom na stavke menija otvaraju se odgovarajuce forme za prikaz
		sobeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SobaricaSpremanjeTableFrame tf = new SobaricaSpremanjeTableFrame(SobaricaFrame.this, managers.getRezervacijeManager(), managers.getSobeManager(), sobarica);
				tf.setVisible(true);
			}
		});

		
		
	}

}
