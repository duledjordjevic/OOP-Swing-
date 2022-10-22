package gui.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import entity.Cene;
import entity.Soba;
import entity.TipSobe;
import gui.SobeZaRezervisanjeZaGosta;
import korisnici.Gost;
import managers.CenovniciManager;
import managers.RezervacijeManager;
import managers.SobeManager;
import net.miginfocom.swing.MigLayout;

public class OdabirUslugaDialog extends JDialog{
	private Soba soba;
	private Gost gost;
	private RezervacijeManager rezervacijeManager;
	private CenovniciManager cenovniciManager;
	private SobeManager sobeManager;
	private LocalDate pocetniDatum, krajnjiDatum;
	private JFrame parent;
	
	// Jedan isti dijalog za Add i Edit
	public OdabirUslugaDialog(JFrame parent, Gost gost, SobeManager sobeManager, Soba soba, RezervacijeManager rezervacijeManager, CenovniciManager cenovniciManager, LocalDate pocetniDatum, LocalDate krajnjiDatum) {
		super(parent, true);
		this.parent = parent;
		this.gost = gost;
		setTitle("Dodatne usluge");
		this.soba = soba;
		this.sobeManager = sobeManager;
		this.rezervacijeManager = rezervacijeManager;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
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

		
		String[] dodatneUsluge = {"Dorucak", "Rucak", "Vecera", "Spa centar"};
		JList lista = new JList(dodatneUsluge);
		add(lista, "span 2");
		add(new JLabel());

		JButton btnCancel = new JButton("Cancel");
		add(btnCancel);

		JButton btnOK = new JButton("OK");
		add(btnOK);

		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int ukupnaCena = 0;
				List lista1 =  (List) lista.getSelectedValuesList();
				TipSobe tipSobe= soba.getTipSobe();
				ArrayList<LocalDate> trazeniDatumi = new ArrayList<LocalDate>();
				LocalDate datum = pocetniDatum;
				while(true) {
					trazeniDatumi.add(datum);
					if(datum.isEqual(krajnjiDatum)) {
						break;
					}
					datum = datum.plusDays(1);
				}
				for(LocalDate date: trazeniDatumi) {
					Cene trazeniCenovnik = null;
					for(Cene cenovnik: cenovniciManager.getCenovnici()) {
						if(cenovnik.getId() != 1 & cenovnik.isActive()) {
							ArrayList<LocalDate> datumi = cenovnik.getDatumi();
							if(datumi.contains(date)) {
								trazeniCenovnik = cenovnik;
							}
						}
					}
					if(trazeniCenovnik == null) {
						trazeniCenovnik = cenovniciManager.getCenovnici().get(0);
					}
					if(tipSobe.equals(TipSobe.JEDNOKREVETNA)) {
						ukupnaCena += trazeniCenovnik.getJednokrevetnaSoba();
					}else if(tipSobe.equals(TipSobe.DVOKREVETNA_SA_JEDNIM_LEZAJEM)) {
						ukupnaCena += trazeniCenovnik.getDvokrevetnaSaJednimLezajemSoba();
					}else if(tipSobe.equals(TipSobe.DVOKREVETNA_SA_DVA_LEZAJA)) {
						ukupnaCena += trazeniCenovnik.getDvokrevetnaSaDvaLezajaSoba();
					}else if(tipSobe.equals(TipSobe.TROKREVETNA)) {
						ukupnaCena += trazeniCenovnik.getTrokrevetnaSoba();
					}
					if(lista1.contains("Dorucak")) {
						ukupnaCena += trazeniCenovnik.getDorucak();
					}
					if(lista1.contains("Rucak")) {
						ukupnaCena += trazeniCenovnik.getRucak();
					}
					if(lista1.contains("Vecera")) {
						ukupnaCena += trazeniCenovnik.getVecera();
					}
					if(lista1.contains("Spa centar")) {
						ukupnaCena += trazeniCenovnik.getSpaCentar();
					}

				}
				int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da rezerviste ovu sobu? Ukupna cena je: " + 
						ukupnaCena , "Potvrda rezervacije", JOptionPane.YES_NO_OPTION);
				if(izbor == JOptionPane.YES_OPTION) {
//					System.out.println(rezervacijeManager == null);
					
					rezervacijeManager.addRezervacija(gost.getUsername(), sobeManager, soba, tipSobe, pocetniDatum, krajnjiDatum, ukupnaCena, trazeniDatumi);
					((SobeZaRezervisanjeZaGosta) parent).refreshData();
					OdabirUslugaDialog.this.dispose();
				}
				
		}	
	});		
	btnCancel.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			OdabirUslugaDialog.this.dispose();
		}
	});
	}
}
