package korisnici;

import entity.Plata;

public class Sobarica extends Zaposleni{
	
	public Sobarica() {
		
	}
	public Sobarica(int id, String ime, String prezime, String pol, String datum, String telefon, String adresa, String username,
			String lozinka, int nivoStrucneSpreme, int staz) {
		super(id, ime, prezime, pol, datum, telefon, adresa, username, lozinka, nivoStrucneSpreme, staz);

	}
	@Override
	public String toFileString() {
		return getId() +" ; " + " sobarica " + " ; "+ getIme() + " ; " + getPrezime() + " ; " + getPol() + " ; " + getDatum() + " ; " + getTelefon() + " ; " + getAdresa() + " ; " + getUsername() + " ; " + getLozinka() + " ; " + getNivoStrucneSpreme() + " ; " + getStaz();
	}
	
}
