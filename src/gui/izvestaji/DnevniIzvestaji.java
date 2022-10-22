package gui.izvestaji;

import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import entity.Rezervacija;
import entity.Soba;
import entity.StatusRezervacije;
import entity.StatusSobe;
import managers.RezervacijeManager;
import managers.SobeManager;
import net.miginfocom.swing.MigLayout;

public class DnevniIzvestaji extends JFrame{


	private static final long serialVersionUID = 6808471321997807090L;
	private SobeManager sobeManager;
	private RezervacijeManager rezervacijeManager;

	public DnevniIzvestaji(RezervacijeManager rezervacijeManager, SobeManager sobeManager) {
		this.sobeManager = sobeManager;
		this.rezervacijeManager = rezervacijeManager;
		
		dnevniIzvestajiFrame();
	}

	private void dnevniIzvestajiFrame() {
		this.setTitle("Dnevni izvestaji");
		this.setSize(200, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("src/img/hotel-building.png").getImage());

		initMainGUI();
		
	}
	
	public void initMainGUI() {
		MigLayout ml = new MigLayout("wrap 2", "[]30[]", "[]20[]20[]20");
		setLayout(ml);
		
		JLabel lblBrojCheckIn = new JLabel("Broj check-inova:");
		add(lblBrojCheckIn);

		
		JLabel txtBrojCheckIn = new JLabel(brojCheckIn());
		add(txtBrojCheckIn);
		
		JLabel lblBrojCheckOut = new JLabel("Broj check-outova:");
		add(lblBrojCheckOut);

		JLabel txtBrojCheckOut = new JLabel(brojCheckOut());
		add(txtBrojCheckOut);
		
		JLabel lblBrojZauzetihSoba = new JLabel("Broj zauzetih soba:");
		add(lblBrojZauzetihSoba);

		JLabel txtBrojZauzetihSoba = new JLabel(brojZauzetihSoba());
		add(txtBrojZauzetihSoba);
		
			
	}
	
	public String brojCheckIn() {
		LocalDate today = LocalDate.now();
		int x = 0;
		for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
			if(rez.getPocetniDatum().isEqual(today)) {
				if(sobeManager.pronadjiSobuPoId(rez.getIdSobe()).getStatusSobe().equals(StatusSobe.ZAUZETA)) {
					x++;
				}
			}
		}
		String ret;
		ret = "" + x;
		return ret;
		
	}
	public String brojCheckOut() {
		LocalDate today = LocalDate.now();
		int x = 0;
		for(Rezervacija rez: rezervacijeManager.getRezervacije()) {
			if(rez.getKrajnjiDatum().isEqual(today) & rez.getStatusRezervacije().equals(StatusRezervacije.POTVRDJENA)) {
				if(sobeManager.pronadjiSobuPoId(rez.getIdSobe()).getStatusSobe().equals(StatusSobe.SPREMANJE) || 
						sobeManager.pronadjiSobuPoId(rez.getIdSobe()).getStatusSobe().equals(StatusSobe.SLOBODNA)) {
					x++;
				}
			}
		}
		String ret;
		ret = "" + x;
		return ret;
		
	}
	public String brojZauzetihSoba() {
		String ret = "";
		int x = 0;
		for(Soba soba: sobeManager.getSobe()) {
			if(soba.getStatusSobe().equals(StatusSobe.ZAUZETA)) {
				x++;
			}
		}
		ret += x;
		return ret;
		
		
	}
	
}
