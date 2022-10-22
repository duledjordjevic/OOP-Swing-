package korisnici;

public class Gost extends Korisnik {
	
	public Gost(){
		
	}
	public Gost(int id, String ime, String prezime, String pol, String datum, String telefon, String adresa, String username,
			String lozinka) {
		super(id, ime, prezime, pol, datum, telefon, adresa, username, lozinka);
		
	}
	@Override
	public String toFileString() {
		return getId() +" ; " + " gost " + " ; "+ getIme() + " ; " + getPrezime() + " ; " + getPol() + " ; " + getDatum() + " ; " + getTelefon() + " ; " + getAdresa() + " ; " + getUsername() + " ; " + getLozinka() + " ; " + " / " + " ; " + " / ";
	}
}
