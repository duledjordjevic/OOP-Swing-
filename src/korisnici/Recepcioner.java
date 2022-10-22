package korisnici;

public class Recepcioner extends Zaposleni {

	public Recepcioner() {
		
	}
	public Recepcioner(int id, String ime, String prezime, String pol, String datum, String telefon, String adresa, String username,
			String lozinka, int nivoStrucneSpreme, int staz) {
		super(id, ime, prezime, pol, datum, telefon, adresa, username, lozinka, nivoStrucneSpreme, staz);

	}
	@Override
	public String toFileString() {
		return getId() +" ; " + " recepcioner " + " ; "+ getIme() + " ; " + getPrezime() + " ; " + getPol() + " ; " + getDatum() + " ; " + getTelefon() + " ; " + getAdresa() + " ; " + getUsername() + " ; " + getLozinka() + " ; " + getNivoStrucneSpreme() + " ; " + getStaz();
	}
	
}
