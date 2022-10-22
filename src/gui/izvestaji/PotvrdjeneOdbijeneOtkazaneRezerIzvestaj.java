package gui.izvestaji;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import entity.IzvestajRezervacija;
import entity.Rezervacija;
import entity.StatusRezervacije;
import managers.RezervacijeManager;
import managers.SobeManager;
import net.miginfocom.swing.MigLayout;

public class PotvrdjeneOdbijeneOtkazaneRezerIzvestaj extends JFrame{
	private SobeManager sobeManager;
	private RezervacijeManager rezervacijeManager;
	private LocalDate pocetniDatum;
	private LocalDate krajnjiDatum;
	private JFrame parent;
	private ArrayList<LocalDate> trazeniDatumi;
	public PotvrdjeneOdbijeneOtkazaneRezerIzvestaj(JFrame parent, LocalDate pocetniDatum, LocalDate krajnjiDatum, RezervacijeManager rezervacijeManager, SobeManager sobeManager) {
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.sobeManager = sobeManager;
		this.rezervacijeManager = rezervacijeManager;
		this.parent = parent;
		ArrayList<LocalDate> trazeniDatumi = new ArrayList<LocalDate>();
		LocalDate datum = pocetniDatum;
		while(true) {
			trazeniDatumi.add(datum);
			if(datum.isEqual(krajnjiDatum)) {
				break;
			}
			datum = datum.plusDays(1);
		}
		
		prihodiRashodiFrame();
	}


	private void prihodiRashodiFrame() {
		this.setTitle("Potvrdjene, odbijene i otkazane rezervacije");
		this.setSize(250, 250);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("src/img/hotel-building.png").getImage());

		initMainGUI();
		
	}
	
	public void initMainGUI() {
		MigLayout ml = new MigLayout("wrap 2", "[]30[]", "[]20[]20[]20");
		setLayout(ml);
		
		JLabel lblPocetniDatum = new JLabel("Od:");
		add(lblPocetniDatum);
		
		String formattedDate1 = pocetniDatum.format(DateTimeFormatter.ofPattern("dd.MMM.yy"));
		JLabel txtPocetniDatum= new JLabel(formattedDate1);
		add(txtPocetniDatum);
		
		JLabel lblKrajnjiDatum = new JLabel("Do:");
		add(lblKrajnjiDatum);

		String formattedDate2 = krajnjiDatum.format(DateTimeFormatter.ofPattern("dd.MMM.yy"));
		JLabel txtKrajnjiDatum= new JLabel(formattedDate2);
		add(txtKrajnjiDatum);
		
		
		JLabel lblPotvrdjene = new JLabel("Broj potvrdjenih: ");
		add(lblPotvrdjene);
		
		JLabel txtPotvrdjene= new JLabel(count(StatusRezervacije.POTVRDJENA));
		add(txtPotvrdjene);
		
		JLabel lblOdbijene = new JLabel("Broj odbijenih: ");
		add(lblOdbijene);

		JLabel txtOdbijene = new JLabel(count(StatusRezervacije.ODBIJENA));
		add(txtOdbijene);
		
		JLabel lblOtkazane= new JLabel("Broj otkazanih: ");
		add(lblOtkazane);

		JLabel txtOtkazane = new JLabel(count(StatusRezervacije.OTKAZANA));
		add(txtOtkazane);
		
		
			
	}
	public String count(StatusRezervacije statusRezervacije) {
		ArrayList<LocalDate> trazeniDatumi = new ArrayList<LocalDate>();
		LocalDate date = pocetniDatum;
		while(true) {
			trazeniDatumi.add(date);
			if(date.isEqual(krajnjiDatum)) {
				break;
			}
			date = date.plusDays(1);
		}
		
		int count = 0;
		
		for(IzvestajRezervacija izvRez: rezervacijeManager.getIzvestajiRezervacija()) {
			if(trazeniDatumi.contains(izvRez.getDatum()) & izvRez.getStatusRezervacije().equals(statusRezervacije)){
				count++;
			}
		}

		
		String ret;
		ret = "" + count;
		return ret;
	}
	

}
