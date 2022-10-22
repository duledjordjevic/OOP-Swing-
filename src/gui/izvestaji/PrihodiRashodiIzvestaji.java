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

public class PrihodiRashodiIzvestaji extends JFrame{
	private SobeManager sobeManager;
	private RezervacijeManager rezervacijeManager;
	private LocalDate pocetniDatum;
	private LocalDate krajnjiDatum;
	private JFrame parent;
	
	public PrihodiRashodiIzvestaji(JFrame parent, LocalDate pocetniDatum, LocalDate krajnjiDatum, RezervacijeManager rezervacijeManager, SobeManager sobeManager) {
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.sobeManager = sobeManager;
		this.rezervacijeManager = rezervacijeManager;
		this.parent = parent;
		prihodiRashodiFrame();
	}


	private void prihodiRashodiFrame() {
		this.setTitle("Prihodi i rashodi");
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
		
		
		JLabel lblPrihodi = new JLabel("Prihodi: ");
		add(lblPrihodi);
		
		JLabel txtPrihodi= new JLabel(prihodi());
		add(txtPrihodi);
		
		JLabel lblRashodi = new JLabel("Rashodi: ");
		add(lblRashodi);

		JLabel txtRashodi = new JLabel(rashodi());
		add(txtRashodi);
		
		
			
	}
	
	public String prihodi() {
		ArrayList<LocalDate> trazeniDatumi = new ArrayList<LocalDate>();
		LocalDate date = pocetniDatum;
		while(true) {
			trazeniDatumi.add(date);
			if(date.isEqual(krajnjiDatum)) {
				break;
			}
			date = date.plusDays(1);
		}
		
		int sum = 0;
		
		for(IzvestajRezervacija izvRez: rezervacijeManager.getIzvestajiRezervacija()) {
			if(trazeniDatumi.contains(izvRez.getDatum()) & izvRez.getStatusRezervacije().equals(StatusRezervacije.POTVRDJENA)){
				for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
					if(rez.getId() == izvRez.getIdRezervacije()) {
						sum += rez.getCena();
					}
				}
			}
		}

		
		String ret;
		ret = "" + sum + "din";
		return ret;
		
	}
	public String rashodi() {
		ArrayList<LocalDate> trazeniDatumi = new ArrayList<LocalDate>();
		LocalDate date = pocetniDatum;
		while(true) {
			trazeniDatumi.add(date);
			if(date.isEqual(krajnjiDatum)) {
				break;
			}
			date = date.plusDays(1);
		}
		
		int sum = 0;
		
		for(IzvestajRezervacija izvRez: rezervacijeManager.getIzvestajiRezervacija()) {
			if(trazeniDatumi.contains(izvRez.getDatum()) & izvRez.getStatusRezervacije().equals(StatusRezervacije.ODBIJENA)){
				for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
					if(rez.getId() == izvRez.getIdRezervacije()) {
						sum += rez.getCena();
					}
				}
			}
		}

		
		String ret;
		ret = "" + sum + "din";
		return ret;
	}

}
