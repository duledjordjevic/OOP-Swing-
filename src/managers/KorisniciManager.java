package managers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import korisnici.Administrator;
import korisnici.Gost;
import korisnici.Korisnik;
import korisnici.Recepcioner;
import korisnici.Sobarica;
import korisnici.Zaposleni;

public class KorisniciManager implements Manager{
	
	private String file;
	private HashMap<String, Administrator> administrator;
	private HashMap<String, Recepcioner> recepcioneri;
	private HashMap<String, Sobarica> sobarice;
	private HashMap<String, Gost> gostiHashMap;
	private ArrayList<Gost> gosti;
	private ArrayList<Zaposleni> zaposleni;
	private ArrayList<Korisnik> korisnici;
	
	public KorisniciManager(String file) {
		this.file = file;
		this.administrator = new HashMap<String, Administrator>();
		this.recepcioneri = new HashMap<String, Recepcioner>();
		this.sobarice = new HashMap<String, Sobarica>();
		this.gostiHashMap = new HashMap<String, Gost>();
		this.zaposleni = new ArrayList<Zaposleni>();
		this.korisnici = new ArrayList<Korisnik>();
		this.gosti = new ArrayList<Gost>();
		
	}
	public Korisnik pronadjiKorisnikaPoId(int id) {
		for(Korisnik k: korisnici) {
			if(id == k.getId()) {
				return k;
			}
			
		}
		return null;
	}
//	public void remove(int id) {
//		Korisnik k = pronadjiKorisnikaPoId(id);
//		this.korisnici.remove(k);
//		this.saveData();
//	}
	
	public void removeZaposleni(int id) {
		Zaposleni k = (Zaposleni) pronadjiKorisnikaPoId(id);
		if (this.recepcioneri.containsKey(k.getUsername())) {
			this.recepcioneri.remove(k.getUsername());
		}else {
			this.sobarice.remove(k.getUsername());
		}
		this.zaposleni.remove(k);
		this.korisnici.remove(k);
		this.saveData();
	}
	
	public HashMap<String, Administrator> getAdministrator() {
		return administrator;
	}

	public void setAdministrator(HashMap<String, Administrator> administrator) {
		this.administrator = administrator;
	}

	public HashMap<String, Recepcioner> getRecepcioneri() {
		return recepcioneri;
	}

	public void setRecepcioneri(HashMap<String, Recepcioner> recepcioneri) {
		this.recepcioneri = recepcioneri;
	}

	public HashMap<String, Sobarica> getSobarice() {
		return sobarice;
	}

	public void setSobarice(HashMap<String, Sobarica> sobarice) {
		this.sobarice = sobarice;
	}

	public HashMap<String, Gost> getGostiHashMap() {
		return gostiHashMap;
	}

	public void setGostiHashMap(HashMap<String, Gost> gosti) {
		this.gostiHashMap = gosti;
	}

	public ArrayList<Zaposleni> getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(ArrayList<Zaposleni> zaposleni) {
		this.zaposleni = zaposleni;
	}
	
	

	public ArrayList<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(ArrayList<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public ArrayList<Gost> getGosti() {
		return gosti;
	}
	public void setGosti(ArrayList<Gost> gosti) {
		this.gosti = gosti;
	}
	
	@Override
	public void loadData() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String line;
			while((line = in.readLine()) != null) {
				line = line.trim();
				if(line.equals("") || line.startsWith("#")) {
					continue;
				}
				
				String[] tokens = line.split(";");
				int id = Integer.parseInt(tokens[0].trim());
				String tip = tokens[1].trim();
				String ime = tokens[2].trim();
				String prezime = tokens[3].trim();
				String pol = tokens[4].trim();
				String datumRodjenja = tokens[5].trim();
				String telefon = tokens[6].trim();
				String adresa = tokens[7].trim();
				String username = tokens[8].trim();
				String lozinka = tokens[9].trim();
				
				
				if (tip.toLowerCase().equals("administrator")) {
					Administrator ret = new Administrator(id, ime, prezime, pol, datumRodjenja, telefon, adresa, username, lozinka);
					this.administrator.put(username, ret);
					this.korisnici.add(ret);
					
				}else if(tip.toLowerCase().equals("recepcioner")){
					int nivoStrucneSpreme = Integer.parseInt(tokens[10].trim());
					int staz = Integer.parseInt(tokens[11].trim());
					Recepcioner ret = new Recepcioner(id, ime, prezime, pol,datumRodjenja, telefon, adresa, username, lozinka, nivoStrucneSpreme, staz);
					this.recepcioneri.put(username, ret);
					this.zaposleni.add(ret);
					this.korisnici.add(ret);
					
				}else if(tip.toLowerCase().equals("sobarica")){
					int nivoStrucneSpreme = Integer.parseInt(tokens[10].trim());
					int staz = Integer.parseInt(tokens[11].trim());
					Sobarica ret = new Sobarica(id, ime, prezime, pol,datumRodjenja, telefon, adresa, username, lozinka, nivoStrucneSpreme, staz);
					this.sobarice.put(username, ret);
					this.korisnici.add(ret);
					this.zaposleni.add(ret);
					
				}else if(tip.toLowerCase().equals("gost")){
					Gost ret = new Gost(id, ime, prezime, pol, datumRodjenja, telefon, adresa, username, lozinka);
					this.gostiHashMap.put(username, ret);
					this.gosti.add(ret);
					this.korisnici.add(ret);
				}
				
			}
			
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public boolean saveData() {
		// TODO Auto-generated method stub
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.file, false));
			pw.println("#ID; tip korisnika ; ime ; prezime ; pol ; datum rodjenja ; telefon ; adresa ; korisnicko ime ; lozinka; nivoStrucneSpreme; staz");
			
			for (Korisnik k : korisnici) {
				
				pw.println(k.toFileString());

			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	
	public void editZaposleni(int id,  String ime, String prezime, String pol, String datumRodjenja, String telefon, String adresa, String username, String lozinka, int nivoStrucneSpreme, int staz) {
		
		Zaposleni k = (Zaposleni) this.pronadjiKorisnikaPoId(id);
		k.setIme(ime);
		k.setPrezime(prezime);
		k.setPol(pol);
		k.setDatum(datumRodjenja);
		k.setTelefon(telefon);
		k.setAdresa(adresa);
		k.setUsername(username);
		k.setLozinka(lozinka);
		k.setNivoStrucneSpreme(nivoStrucneSpreme);
		k.setStaz(staz);

		this.saveData();
	}
	public void addZaposleni(String tipKorisnika, String ime, String prezime, String pol, String datumRodjenja, String telefon, String adresa, String username, String lozinka, int nivoStrucneSpreme, int staz) {
		int id = korisnici.get(korisnici.size() - 1).getId() + 1;
		if(tipKorisnika.equals("recepcioner")) {
			Recepcioner recepcioner = new Recepcioner(id, ime, prezime, pol, datumRodjenja, telefon, adresa, username, lozinka, nivoStrucneSpreme, staz);
			this.recepcioneri.put(username, recepcioner);
			this.korisnici.add(recepcioner);
			this.zaposleni.add(recepcioner);
		}else {
			Sobarica sobarica = new Sobarica(id, ime, prezime, pol, datumRodjenja, telefon, adresa, username, lozinka, nivoStrucneSpreme, staz);
			this.sobarice.put(username, sobarica);
			this.korisnici.add(sobarica);
			this.zaposleni.add(sobarica);
		}
		this.saveData();		
	}
	public void addGost(String ime, String prezime, String pol, String datumRodjenja, String telefon, String adresa, String username, String lozinka) {
		int id = korisnici.get(korisnici.size() - 1).getId() + 1;
		Gost gost = new Gost(id, ime, prezime, pol, datumRodjenja, telefon, adresa, username, lozinka);
		this.gosti.add(gost);
		this.gostiHashMap.put(gost.getUsername(), gost);
		this.korisnici.add(gost);
		this.saveData();
		
	}

	
	public void editGost(int id, String ime, String prezime, String pol, String datumRodjenja, String telefon, String adresa,
			String username, String lozinka) {
		Gost gost = (Gost) this.pronadjiKorisnikaPoId(id);
		gost.setIme(ime);
		gost.setPrezime(prezime);
		gost.setPol(pol);
		gost.setDatum(datumRodjenja);
		gost.setTelefon(telefon);
		gost.setAdresa(adresa);
		gost.setUsername(username);
		gost.setLozinka(lozinka);
		
	}
	public void removeGost(int id) {
		Gost gost = (Gost) pronadjiKorisnikaPoId(id);
		this.gosti.remove(gost);
		this.gostiHashMap.remove(gost.getUsername());
		this.korisnici.remove(gost);
		this.saveData();
		
	}

	
}
