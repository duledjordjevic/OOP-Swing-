package gui.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;

import entity.TipSobe;
import gui.SobeZaRezervisanjeZaGosta;
import korisnici.Gost;
import managers.CenovniciManager;
import managers.RezervacijeManager;
import managers.SobeManager;
import net.miginfocom.swing.MigLayout;

public class FiltriranjeSobaZaGostaDialog extends JDialog{

	private static final long serialVersionUID = -2107192443798865551L;
	
	private SobeManager sobeManager;
	private Gost gost;
	private CenovniciManager cenovniciManager;
	private RezervacijeManager rezervacijeManager;
	private JFrame parent;
	
	// Jedan isti dijalog za Add i Edit
	public FiltriranjeSobaZaGostaDialog(JFrame parent, Gost gost, SobeManager sobeManager, RezervacijeManager rezervacijeManager, CenovniciManager cenovniciManager) {
		super(parent, true);
		this.gost = gost;
		this.parent = parent;
		setTitle("Filtriranje soba");
		this.sobeManager = sobeManager;
		this.rezervacijeManager = rezervacijeManager;;
		this.cenovniciManager = cenovniciManager;
		
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

		JComboBox combobox = new JComboBox(TipSobe.values());
		add(combobox, "span 2");
		add(new JLabel());
		
		
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
		
		JLabel lblDodatneUsluge = new JLabel("Dodatne usluge");
		add(lblDodatneUsluge);
		
		String[] dodatneUsluge = {"Klima", "Balkon", "Tv"};
		JList lista = new JList(dodatneUsluge);
		add(lista, "span 2");

		JButton btnCancel = new JButton("Cancel");
		add(btnCancel);

		JButton btnOK = new JButton("OK");
		add(btnOK);

		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
					TipSobe tipSobe = TipSobe.valueOf(combobox.getSelectedItem().toString());
					
					if (chooser1.getDate() != null & chooser2.getDate() != null) {
						
						LocalDate pocetniDatum = chooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						LocalDate krajnjiDatum = chooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						Date today = new Date();
						LocalDate danasnjiDan = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
						
						if(danasnjiDan.compareTo(pocetniDatum) <= 0 & danasnjiDan.compareTo(krajnjiDatum) <= 0) {
							if(pocetniDatum.compareTo(krajnjiDatum) <= 0) {
								List lista1 =  (List) lista.getSelectedValuesList();
								boolean klima = false, balkon = false, tv = false ;
								if(lista1.contains("Klima")) {
									klima = true;
								}
								if(lista1.contains("Balkon")) {
									balkon = true;
								}
								if(lista1.contains("Tv")) {
									tv = true;
								}
								
								SobeZaRezervisanjeZaGosta tf = new SobeZaRezervisanjeZaGosta(parent, gost, sobeManager , rezervacijeManager, cenovniciManager, tipSobe, pocetniDatum, krajnjiDatum, klima, balkon, tv);
								tf.setVisible(true);
								FiltriranjeSobaZaGostaDialog.this.dispose();
							}else {
								JOptionPane.showMessageDialog(null, "Pocetni datum ne moze biti posle krajnjeg", "Greska", JOptionPane.WARNING_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(null, "Ne mozete uneti datume iz proslosti", "Greska", JOptionPane.WARNING_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Morate popuniti sva polja", "Greska", JOptionPane.WARNING_MESSAGE);
					}
				
		}	
	});		
	btnCancel.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			FiltriranjeSobaZaGostaDialog.this.dispose();
		}
	});
	}};
