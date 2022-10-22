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

import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import gui.izvestaji.BiranjeDatumaDialog;
import gui.izvestaji.DnevniIzvestaji;
import managers.ManagerFactory;

public class AdminFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ManagerFactory managers;
	
	public AdminFrame(ManagerFactory managers) {
		this.managers = managers;
		
		adminFrame();
	}

	private void adminFrame() {
		this.setTitle("Admin");
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("src/img/hotel-building.png").getImage());

		initMainGUI();
		
	}

	private void initMainGUI() {
		JMenuBar mainMenu = new JMenuBar();
		
		JMenu korisniciMenu = new JMenu("Korisnici");
		JMenuItem zaposleniiItem = new JMenuItem("Zaposleni");
		JMenuItem gostiItem = new JMenuItem("Gosti");
		korisniciMenu.add(zaposleniiItem);
		korisniciMenu.add(gostiItem);
		mainMenu.add(korisniciMenu);
		
		JMenu rezervacijeMenu = new JMenu("Rezervacije");
		JMenuItem rezervacijeItem = new JMenuItem("Sve rezervacije");
		rezervacijeMenu.add(rezervacijeItem);
		mainMenu.add(rezervacijeMenu);
		
		rezervacijeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SveRezervacijeTableFrame tf = new SveRezervacijeTableFrame(AdminFrame.this, managers.getRezervacijeManager());
				tf.setVisible(true);
				
			}
			
		});
		JMenu sobeMenu = new JMenu("Sobe");
		JMenuItem sobeItem = new JMenuItem("Sve sobe");
		sobeMenu.add(sobeItem);

		mainMenu.add(sobeMenu);
		
		sobeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SobeTableFrame tf = new SobeTableFrame(AdminFrame.this, managers.getSobeManager());
				tf.setVisible(true);
				
			}
			
		});
		
		JMenu cenovnikMenu = new JMenu("Cenovnici");
		JMenuItem cenovnikItem = new JMenuItem("Cenovnik");
		cenovnikMenu.add(cenovnikItem);
		mainMenu.add(cenovnikMenu);
		
		cenovnikItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CenovniciTableFrame tf = new CenovniciTableFrame(AdminFrame.this, managers.getCenovniciManager());
				tf.setVisible(true);
				
			}
			
		});
		
		JMenu izvestajiMenu = new JMenu("Izvestaji");
		JMenuItem dnevniIzvestajiItem = new JMenuItem("Dnevni izvestaji");
		izvestajiMenu.add(dnevniIzvestajiItem);
		JMenuItem prihodiRashodiItem = new JMenuItem("Prihodi i rashodi");
		izvestajiMenu.add(prihodiRashodiItem);
		JMenuItem rezervacijeIzvestajiItem = new JMenuItem("Potvrdjene, odbijene i otkazane rezervacije");
		izvestajiMenu.add(rezervacijeIzvestajiItem);
		JMenuItem brojSpremljenihSobaItem = new JMenuItem("Broj spremljenih soba pojedinacne sobarice");
		izvestajiMenu.add(brojSpremljenihSobaItem);
		JMenuItem sobeIzvestajiItem = new JMenuItem("Broj nocenja i prihod po sobi");
		izvestajiMenu.add(sobeIzvestajiItem);
		JMenu grafickiMenu = new JMenu("Graficki izvestaji");
		JMenuItem prihodPoSobiItem = new JMenuItem("Prihod po sobi i ukupan prihod");
		JMenuItem opterecenjeSobaricaItem = new JMenuItem("Opterecenje sobarica");
		JMenuItem statusRezervacijaItem = new JMenuItem("Status rezervacija");
		grafickiMenu.add(prihodPoSobiItem);
		grafickiMenu.add(opterecenjeSobaricaItem);
		grafickiMenu.add(statusRezervacijaItem);
		izvestajiMenu.add(grafickiMenu);
		mainMenu.add(izvestajiMenu);
		
//		Grafici grafik = new Grafici(controlers.getRezervacijeManager(), controlers.getKorisniciManager());
		
		prihodPoSobiItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Grafici grafik = new Grafici(managers.getRezervacijeManager(), managers.getKorisniciManager());
//				grafik.godisnjiPrikazPrihodaPoSobama();
				XYChart chart = grafik.getGodisnjiPrikazXYChart();
				
				Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new SwingWrapper(chart).displayChart().setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    }
                });
                t.start();
			}	
		});
		opterecenjeSobaricaItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Grafici grafik = new Grafici(managers.getRezervacijeManager(), managers.getKorisniciManager());
				PieChart pita = grafik.getChartSobarice();
				
				Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new SwingWrapper(pita).displayChart().setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    }
                });
                t.start();
			}	
		});
		statusRezervacijaItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Grafici grafik = new Grafici(managers.getRezervacijeManager(), managers.getKorisniciManager());
				PieChart pita = grafik.getChartStatusRez();
				
				Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new SwingWrapper(pita).displayChart().setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    }
                });
                t.start();
			}	
		});
		
		sobeIzvestajiItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				BiranjeDatumaDialog frame = new BiranjeDatumaDialog(AdminFrame.this, managers.getSobeManager() ,managers.getRezervacijeManager(),managers.getKorisniciManager(), "Sobe izvestaji");
				frame.setVisible(true);
			}
		});
		
		brojSpremljenihSobaItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				BiranjeDatumaDialog frame = new BiranjeDatumaDialog(AdminFrame.this, managers.getSobeManager() ,managers.getRezervacijeManager(),managers.getKorisniciManager(), "Broj spremljenih soba sobarica");
				frame.setVisible(true);
			}
		});
		
		dnevniIzvestajiItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				DnevniIzvestaji frame = new DnevniIzvestaji(managers.getRezervacijeManager(), managers.getSobeManager());
				frame.setVisible(true);
			}
		});
		prihodiRashodiItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				BiranjeDatumaDialog frame = new BiranjeDatumaDialog(AdminFrame.this, managers.getSobeManager() ,managers.getRezervacijeManager(), managers.getKorisniciManager(), "Prihodi i rashodi");
				frame.setVisible(true);
			}
		});
		rezervacijeIzvestajiItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				BiranjeDatumaDialog frame = new BiranjeDatumaDialog(AdminFrame.this, managers.getSobeManager() ,managers.getRezervacijeManager(), managers.getKorisniciManager(), "Potvrdjene, odbijene i otkazane");
				frame.setVisible(true);
			}
		});
		

		zaposleniiItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ZaposleniTableFrame tf = new ZaposleniTableFrame(AdminFrame.this, managers.getKorisniciManager());
				tf.setVisible(true);
			}
		});
		gostiItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GostiTableFrame tf = new GostiTableFrame(AdminFrame.this, managers.getKorisniciManager(), managers.getSobeManager(), managers.getRezervacijeManager(), managers.getCenovniciManager());
				tf.setVisible(true);
				
			}
			
		});

		this.setJMenuBar(mainMenu);
	
		
	}
	
	
	
	
}
