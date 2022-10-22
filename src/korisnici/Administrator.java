package korisnici;

public class Administrator extends Korisnik {
//	private static Administrator instance = new Administrator();
	public Administrator() {
		
	}
	public Administrator(int id, String ime, String prezime, String pol, String datum, String telefon, String adresa, String username,
			String lozinka) {
		super(id, ime, prezime, pol, datum, telefon, adresa, username, lozinka);
	}
	
//	public static Administrator getInstance() {
//		if (instance == null) {
//			instance = new Administrator();
//		}
//		return instance;
//	}
	@Override
	public String toFileString() {
		return getId() +" ; " + " administrator " + " ; "+ getIme() + " ; " + getPrezime() + " ; " + getPol() + " ; " + getDatum() + " ; " + getTelefon() + " ; " + getAdresa() + " ; " + getUsername() + " ; " + getLozinka() + " ; " + " / " + " ; " + " / ";
	}
	
	@Override
	public String toString() {
		return "Administrator [getIme()=" + getIme() + ", getPrezime()=" + getPrezime() + ", getPol()=" + getPol()
				+ ", getDatum()=" + getDatum() + ", getTelefon()=" + getTelefon() + ", getAdresa()=" + getAdresa()
				+ ", getLozinka()=" + getLozinka() + ", getUsername()=" + getUsername() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
