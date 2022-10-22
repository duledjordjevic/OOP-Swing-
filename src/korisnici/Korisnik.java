package korisnici;


public abstract class Korisnik {
	private int id;
	private String ime;
	private String prezime;
	private String pol;
	private String datum;
	private String telefon;
	private String adresa;
	private String username;
	private String lozinka;

	
	public Korisnik() {
		
	}
	public Korisnik(int id, String ime, String prezime, String pol, String datum, String telefon, String adresa, String username,
			String lozinka) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datum = datum;
		this.telefon = telefon;
		this.adresa = adresa;
		this.username = username;
		this.lozinka = lozinka;

		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String toFileString() {
		return null;
	}
	

}
