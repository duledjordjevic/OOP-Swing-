package korisnici;

import entity.Plata;

public abstract class Zaposleni extends Korisnik{
	private Plata plata;
	private int nivoStrucneSpreme;
	private int staz;
	
	public Zaposleni() {
		
	}
	
	public Zaposleni(int id, String ime, String prezime, String pol, String datum, String telefon, String adresa, String username,
			String lozinka, int nivoStrucneSpreme, int staz) {
		super(id, ime, prezime, pol, datum, telefon, adresa, username, lozinka);
		this.nivoStrucneSpreme = nivoStrucneSpreme;
		this.staz = staz;
		
	}

	public Plata getPlata() {
		return plata;
	}

	public void setPlata(Plata plata) {
		this.plata = plata;
	}

	public int getNivoStrucneSpreme() {
		return nivoStrucneSpreme;
	}

	public void setNivoStrucneSpreme(int nivoStrucneSpreme) {
		this.nivoStrucneSpreme = nivoStrucneSpreme;
	}

	public int getStaz() {
		return staz;
	}

	public void setStaz(int staz) {
		this.staz = staz;
	}

	
}
