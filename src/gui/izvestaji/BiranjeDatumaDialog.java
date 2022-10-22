package gui.izvestaji;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;

import managers.KorisniciManager;
import managers.RezervacijeManager;
import managers.SobeManager;
import net.miginfocom.swing.MigLayout;

public class BiranjeDatumaDialog extends JDialog{

	private SobeManager sobeManager;
	private RezervacijeManager rezervacijeManager;
	private KorisniciManager korisniciManager;
	private String izvestaj;
	private JFrame parent;
	
	// Jedan isti dijalog za Add i Edit
	public BiranjeDatumaDialog(JFrame parent,  SobeManager sobeManager, RezervacijeManager rezervacijeManager, KorisniciManager korisniciManager,  String izvestaj) {
		super(parent, true);
		this.parent = parent;
		this.izvestaj = izvestaj;
		setTitle("Biranje datuma");
		this.sobeManager = sobeManager;
		this.rezervacijeManager = rezervacijeManager;;
		this.korisniciManager = korisniciManager;
		
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
		

		JButton btnCancel = new JButton("Cancel");
		add(btnCancel);

		JButton btnOK = new JButton("OK");
		add(btnOK);

		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
					
					if (chooser1.getDate() != null & chooser2.getDate() != null) {
						
						LocalDate pocetniDatum = chooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						LocalDate krajnjiDatum = chooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						Date today = new Date();
						LocalDate danasnjiDan = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
						
						if(pocetniDatum.compareTo(krajnjiDatum) <= 0) {
							if(izvestaj.equals("Prihodi i rashodi")) {
								PrihodiRashodiIzvestaji tf = new PrihodiRashodiIzvestaji( parent, pocetniDatum, krajnjiDatum, rezervacijeManager , sobeManager  );
								tf.setVisible(true);
								BiranjeDatumaDialog.this.dispose();
							}else if(izvestaj.equals("Potvrdjene, odbijene i otkazane")){
								PotvrdjeneOdbijeneOtkazaneRezerIzvestaj tf = new PotvrdjeneOdbijeneOtkazaneRezerIzvestaj( parent, pocetniDatum, krajnjiDatum, rezervacijeManager , sobeManager  );
								tf.setVisible(true);
								BiranjeDatumaDialog.this.dispose();
							}else if(izvestaj.equals("Broj spremljenih soba sobarica")) {
								BrojSpremljenihSobaSobaricaTableFrame tf = new BrojSpremljenihSobaSobaricaTableFrame(parent, rezervacijeManager , korisniciManager, pocetniDatum, krajnjiDatum  );
								tf.setVisible(true);
								BiranjeDatumaDialog.this.dispose();
							}else if(izvestaj.equals("Sobe izvestaji")) {
								SobeIzvestajTableFrame tf = new SobeIzvestajTableFrame(parent, rezervacijeManager , sobeManager, pocetniDatum, krajnjiDatum  );
								tf.setVisible(true);
								BiranjeDatumaDialog.this.dispose();
							}
							
						}else {
							JOptionPane.showMessageDialog(null, "Pocetni datum ne moze biti posle krajnjeg", "Greska", JOptionPane.WARNING_MESSAGE);
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
					}
				
		}	
	});		
	btnCancel.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			BiranjeDatumaDialog.this.dispose();
		}
	});
	}
};

