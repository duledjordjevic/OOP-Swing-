package managers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import entity.IzvestajRezervacija;
import entity.Rezervacija;
import entity.Soba;
import entity.StatusRezervacije;
import entity.TipSobe;
import korisnici.Sobarica;

public class RezervacijeManager implements Manager{

	private String file1;
	private String file2;
	private ArrayList<Rezervacija> rezervacije;
	private ArrayList<IzvestajRezervacija> izvestajiRezervacija;
	
	public RezervacijeManager() {
		
	}
	
	public RezervacijeManager(String file1, String file2) {
		this.file1 = file1;
		this.file2 = file2;
		this.rezervacije = new ArrayList<Rezervacija>();
		this.izvestajiRezervacija = new ArrayList<IzvestajRezervacija>();
	}
	
	public void odbijanjeRezervacija() {
		for(Rezervacija rezervacija: rezervacije) {
			LocalDate today = LocalDate.now();
			if(rezervacija.getPocetniDatum().isBefore(today) & rezervacija.getStatusRezervacije().equals(StatusRezervacije.NA_CEKANJU)) {
				rezervacija.setStatusRezervacije(StatusRezervacije.ODBIJENA);
				this.saveData();
				break;
			}
		}
	}
	
	@Override
	public void loadData() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file1), "utf-8"));
			String line;
			while((line = in.readLine()) != null) {
				line = line.trim();
				if(line.equals("") || line.startsWith("#")) {
					continue;
				}
				
				String[] tokens = line.split(";");
				int id = Integer.parseInt(tokens[0].trim());
				String username = tokens[1].trim();
				TipSobe tipSobe = TipSobe.valueOf(tokens[2].trim().toUpperCase());
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
				
				LocalDate pocetniDatum = LocalDate.parse(tokens[3].trim(), formatter);
				LocalDate krajnjiDatum = LocalDate.parse(tokens[4].trim(), formatter);
				
				StatusRezervacije statusRezervacije = StatusRezervacije.valueOf(tokens[5].trim().toUpperCase());
				int cena = Integer.parseInt(tokens[6].trim());
				int idSobe = Integer.parseInt(tokens[7].trim());
				String sobaricaIme = tokens[8].trim();
				Rezervacija ret = new Rezervacija(id, username, tipSobe, pocetniDatum, krajnjiDatum, statusRezervacije, cena, idSobe, sobaricaIme);
				this.rezervacije.add(ret);
				
			}
			
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file2), "utf-8"));
			
			String line;
			while((line = in.readLine()) != null) {
				line = line.trim();
				if(line.equals("") || line.startsWith("#")) {
					continue;
				}
				
				String[] tokens = line.split(";");
				int id = Integer.parseInt(tokens[0].trim());
				int idRezervacije = Integer.parseInt(tokens[1].trim());
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
				LocalDate datum = LocalDate.parse(tokens[2].trim(), formatter);
				
				
				StatusRezervacije statusRezervacije = StatusRezervacije.valueOf(tokens[3].trim().toUpperCase());

				IzvestajRezervacija ret = new IzvestajRezervacija(id, idRezervacije, datum, statusRezervacije);
				this.izvestajiRezervacija.add(ret);
				
				
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
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.file1, false));
			pw.println("#id; username; tip sobe; pocetni datum; krajnji datum; status rezervacije; cena ; id sobe ; sobarica");
			
			for (Rezervacija rez : rezervacije) {
				pw.println(rez.toFileString());
				
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		
		PrintWriter pw1 = null;
		try {
			pw1 = new PrintWriter(new FileWriter(this.file2, false));
			pw1.println("#id; id rezervacije; datum ; status rezervacije");
			
			for (IzvestajRezervacija x : izvestajiRezervacija) {
				pw1.println(x.toFileString());
				
			}
			pw1.close();
		} catch (IOException e) {
			return false;
		}
		return true;
		
	}

	public ArrayList<Rezervacija> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(ArrayList<Rezervacija> rezervacije) {
		this.rezervacije = rezervacije;
	}
	

	public ArrayList<IzvestajRezervacija> getIzvestajiRezervacija() {
		return izvestajiRezervacija;
	}

	public void setIzvestajiRezervacija(ArrayList<IzvestajRezervacija> izvestajiRezervacija) {
		this.izvestajiRezervacija = izvestajiRezervacija;
	}

	public Rezervacija pronadjiRezervacijuPoId(int id) {
		for(Rezervacija rez: rezervacije) {
			if(id == rez.getId()) {
				return rez;
			}
			
		}
		return null;

	}
//	public void removeRezervaciju(int id) {
//		int x = 0;
//		for(int i = 0; i < rezervacije.size(); i++) {
//			if(rezervacije.get(i).getId() == id) {
//				x = i;
//				break;
//			}
//		}
//		this.rezervacije.remove(x);
//		this.saveData();
//	}
//
//	public void editRezervacija(int id, TipSobe tipSobe, LocalDate pocetniDatum, LocalDate krajnjiDatum) {
//		Rezervacija rez = (Rezervacija) this.pronadjiRezervacijuPoId(id);
//		
//		rez.setTipSobe(tipSobe);
//		rez.setPocetniDatum(pocetniDatum);
//		rez.setKrajnjiDatum(krajnjiDatum);
//		
//		this.saveData();
//		
//	}

	public void addRezervacija(String username, SobeManager sobeManager, Soba soba, TipSobe tipSobe, LocalDate pocetniDatum, LocalDate krajnjiDatum, int cena, ArrayList<LocalDate> trazeniDatumi) {
		int id = rezervacije.get(rezervacije.size() - 1).getId() + 1;
		int idSobe = soba.getId();
		sobeManager.obrisiDatume(trazeniDatumi, soba.getId());

		Rezervacija rezervacija = new Rezervacija(id, username, tipSobe, pocetniDatum, krajnjiDatum, StatusRezervacije.NA_CEKANJU, cena, idSobe, " / ");
		this.rezervacije.add(rezervacija);
		addIzvestajRezervacija(id, StatusRezervacije.NA_CEKANJU);
		this.saveData();
	}
	public void addIzvestajRezervacija(int idRezervacije,  StatusRezervacije statusRezervacije) {
		int id = izvestajiRezervacija.get(izvestajiRezervacija.size() - 1).getId() + 1;
		LocalDate datum = LocalDate.now();
		IzvestajRezervacija izvRez = new IzvestajRezervacija(id, idRezervacije, datum, statusRezervacije);
		
		this.izvestajiRezervacija.add(izvRez);
		this.saveData();
	}

	public void otkaziRezervaciju(int id) {
		Rezervacija rez = (Rezervacija) this.pronadjiRezervacijuPoId(id);
		
		rez.setStatusRezervacije(StatusRezervacije.OTKAZANA);
		addIzvestajRezervacija(rez.getId(), StatusRezervacije.OTKAZANA);
		
		this.saveData();
		
	}

//	public void checkIn(int id) {
//		Rezervacija rez = (Rezervacija) this.pronadjiRezervacijuPoId(id);
//		
//		rez.setStatusRezervacije(StatusRezervacije.POTVRDJENA);
//		
//		
//		this.saveData();
//		
//	}
	public void cancelRezervaciju(int id) {
		Rezervacija rez = (Rezervacija) this.pronadjiRezervacijuPoId(id);
		
//		rez.setCena(0);
		rez.setStatusRezervacije(StatusRezervacije.ODBIJENA);
		addIzvestajRezervacija(rez.getId(), StatusRezervacije.ODBIJENA);
		
		this.saveData();
	}
	public void checkedRezervaciju(int id) {
		Rezervacija rez = (Rezervacija) this.pronadjiRezervacijuPoId(id);
		
		rez.setStatusRezervacije(StatusRezervacije.POTVRDJENA);
		addIzvestajRezervacija(rez.getId(), StatusRezervacije.POTVRDJENA);
		
		this.saveData();
	}

	public void dodeljivanjeSobeSobarici(int id, RezervacijeManager rezervacijeManager, KorisniciManager korisniciManager) {
		Rezervacija rez = this.pronadjiRezervacijuPoId(id);
		HashMap<String, Integer> sobaricePoslovi = new HashMap<String, Integer>();
		for(Rezervacija rezervacija: rezervacijeManager.getRezervacije()) {
			if(sobaricePoslovi.containsKey(rezervacija.getSobaricaIme())){
				sobaricePoslovi.put(rezervacija.getSobaricaIme(), sobaricePoslovi.get(rezervacija.getSobaricaIme()) + 1);	
			}
		}
		HashMap<String, Sobarica> sveSobarice = korisniciManager.getSobarice();
		for(String imeSobarice: sveSobarice.keySet()) {
			if(!sobaricePoslovi.containsKey(imeSobarice)) {
				sobaricePoslovi.put(imeSobarice, 0);
			}
		}
		
		int max = 10000;
		String sobarica = null;
		for(String imeSobarice: sobaricePoslovi.keySet()) {
			if(sobaricePoslovi.get(imeSobarice) < max) {
				max = sobaricePoslovi.get(imeSobarice);
				sobarica = imeSobarice;
			}
		}
		
		rez.setSobaricaIme(sobarica);
		
		this.saveData();
		
	}

	public int getMesecniPrihodi(TipSobe ts, LocalDate pocetniDatum, LocalDate krajnjiDatum) {
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
		
		for(Rezervacija rez: rezervacije) {
			ArrayList<LocalDate> rezDatumi = new ArrayList<LocalDate>();
			LocalDate datum = rez.getPocetniDatum();
			while(true) {
				rezDatumi.add(datum);
				if(datum.isEqual(rez.getKrajnjiDatum())) {
					break;
				}
				datum = datum.plusDays(1);
			}
			
			if(rez.getTipSobe().equals(ts) & (rez.getStatusRezervacije().equals(StatusRezervacije.POTVRDJENA) || rez.getStatusRezervacije().equals(StatusRezervacije.OTKAZANA))) {
				int count = 0;
				
				
				for(LocalDate date1: rezDatumi) {
					if(trazeniDatumi.contains(date1)) {
						count++;
					}
				}
//				System.out.println((rez.getCena()/rezDatumi.size()) * count);
				sum += (rez.getCena()/rezDatumi.size()) * count;
//				if(count != 0) {
//					System.out.println(rez.getCena()/rezDatumi.size() + " / " +count);
//				}
//				System.out.println(sum);
			}
			

		}

//		System.out.println(sum);
		return sum;
	}

	
	
	

}
