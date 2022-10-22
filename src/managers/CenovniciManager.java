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

import entity.Cene;
import entity.Soba;
import korisnici.Korisnik;

public class CenovniciManager implements Manager {
	private String file;
	
	private ArrayList<Cene> cenovnici;
	public CenovniciManager() {
		
	}
	public CenovniciManager(String file) {
		this.file = file;
		this.cenovnici = new ArrayList<Cene>();
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
				int jednokrevetnaSoba = Integer.parseInt(tokens[1].trim());
				int dvokrevetnaSaJednimLezajemSoba = Integer.parseInt(tokens[2].trim());
				int dvokrevetnaSaDvaLezajaSoba = Integer.parseInt(tokens[3].trim());
				int trokrevetnaSoba = Integer.parseInt(tokens[4].trim());
				int dorucak = Integer.parseInt(tokens[5].trim());
				int rucak = Integer.parseInt(tokens[6].trim());
				int vecera = Integer.parseInt(tokens[7].trim());
				int spaCentar = Integer.parseInt(tokens[8].trim());
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
				LocalDate pocetniDatum = LocalDate.parse(tokens[9].trim(), formatter);
				LocalDate krajnjiDatum = LocalDate.parse(tokens[10].trim(), formatter);
				
				boolean isActive = true;
				if(tokens[11].trim().equals("ne")) {
					isActive = false;
				}
				
				Cene ret = new Cene(id, jednokrevetnaSoba, dvokrevetnaSaJednimLezajemSoba,dvokrevetnaSaDvaLezajaSoba, trokrevetnaSoba
						, dorucak, rucak, vecera, spaCentar, pocetniDatum, krajnjiDatum, isActive);
				this.cenovnici.add(ret);
				
				
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
			pw = new PrintWriter(new FileWriter(this.file, false));
			pw.println("#id; JEDNOKREVETNA ; DVOKREVETNA_SA_JEDNIM_LEZAJEM; DVOKREVETNA_SA_DVA_LEZAJA; TROKREVETNA; dorucak; rucak; vecera; spa centar; pocetni datum; krajnji datum ; aktivan");
			
			for (Cene cena : cenovnici) {
				pw.println(cena.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	public ArrayList<Cene> getCenovnici() {
		return cenovnici;
	}
	public void setCenovnici(ArrayList<Cene> cenovnici) {
		this.cenovnici = cenovnici;
	}
	public Cene pronadjiCenovnikPoId(int id) {
		for(Cene cena: cenovnici) {
			if(id == cena.getId()) {
				return cena;
			}
			
		}
		return null;
		
	}
//	public void editCenovnik(int id, int jednokrevetna, int dvokrevetnaSaJednimLezajem, int dvokrevetnaSaDvaLezaja, int trokrevetna,
//			int dorucak, int rucak, int vecera, int spaCentar, LocalDate pocetniDatum, LocalDate krajnjiDatum) {
//		Cene cenovnik = this.pronadjiCenovnikPoId(id);
//		
//		cenovnik.setJednokrevetnaSoba(jednokrevetna);
//		cenovnik.setDvokrevetnaSaJednimLezajemSoba(dvokrevetnaSaJednimLezajem);
//		cenovnik.setDvokrevetnaSaDvaLezajaSoba(dvokrevetnaSaDvaLezaja);
//		cenovnik.setTrokrevetnaSoba(trokrevetna);
//		cenovnik.setDorucak(dorucak);
//		cenovnik.setRucak(rucak);
//		cenovnik.setVecera(vecera);
//		cenovnik.setSpaCentar(spaCentar);
//		cenovnik.setPocetniDatum(pocetniDatum);
//		cenovnik.setKrajnjiDatum(krajnjiDatum);
//		
//		this.saveData();
//		
//	}
	public void addCenovnik(int jednokrevetna, int dvokrevetnaSaJednimLezajem, int dvokrevetnaSaDvaLezaja,
			int trokrevetna, int dorucak, int rucak, int vecera, int spaCentar, LocalDate pocetniDatum,
			LocalDate krajnjiDatum, boolean isActive) {
		
		int id = cenovnici.get(cenovnici.size() - 1).getId() + 1;
		
		Cene cenovnik = new Cene(id, jednokrevetna, dvokrevetnaSaJednimLezajem, dvokrevetnaSaDvaLezaja, trokrevetna, dorucak,
				rucak, vecera, spaCentar, pocetniDatum, krajnjiDatum, isActive);
		cenovnici.add(cenovnik);
		this.saveData();
		
	}
	public void cancelCenovnik(int id) {
		Cene cenovnik = this.pronadjiCenovnikPoId(id);
//		
		cenovnik.setActive(false);
		this.saveData();
	}
//	public void removeCenovnik(int id) {
//		Cene cenovnik = this.pronadjiCenovnikPoId(id);
//		
//		this.cenovnici.remove(cenovnik);
//		this.saveData();
//		
//	}
	
	
}
