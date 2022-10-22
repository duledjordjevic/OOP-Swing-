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

import entity.Rezervacija;
import entity.Soba;
import entity.StatusSobe;
import entity.TipSobe;

public class SobeManager implements Manager{
	private String file;
	private ArrayList<Soba> sobe;
	
	
	public SobeManager() {
		
	}
	public SobeManager(String file) {
		this.file = file;
		this.sobe = new ArrayList<Soba>();
		
		
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
				TipSobe tipSobe = TipSobe.valueOf(tokens[1].trim().toUpperCase());
				StatusSobe statusSobe = StatusSobe.valueOf(tokens[2].trim().toUpperCase());
				boolean klima = false, balkon = false, tv = false;
				if(tokens[3].trim().equals("da")) {
					klima = true;
				}
				if(tokens[4].trim().equals("da")) {
					balkon = true;
				}
				if(tokens[5].trim().equals("da")) {
					tv = true;
				}
				String[] datumiTokens = tokens[6].trim().split("/");
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
				
				ArrayList<LocalDate> sviDatumi = new ArrayList<LocalDate>();
				
				for(String datum: datumiTokens) {
					LocalDate formatiranDatum = LocalDate.parse(datum, formatter);
					sviDatumi.add(formatiranDatum);
				}
				
				
				Soba ret = new Soba(id, tipSobe, statusSobe, klima, balkon, tv,  sviDatumi);
				this.sobe.add(ret);
				
				
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
			pw.println("#id; tip sobe; status sobe; klima; balkon; tv; slobodni datumi");
			
			for (Soba soba : sobe) {
				pw.println(soba.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	public ArrayList<Soba> getSobe() {
		return sobe;
	}
	public void setSobe(ArrayList<Soba> sobe) {
		this.sobe = sobe;
	}
	public Soba pronadjiSobuPoId(int id) {
		for(Soba s: sobe) {
			if(id == s.getId()) {
				return s;
			}
			
		}
		return null;
		
	}
	public void removeSobu(int id) {
		int x = 0;
		for(int i = 0; i < sobe.size(); i++) {
			if(sobe.get(i).getId() == id) {
				x = i;
				break;
			}
		}
		sobe.remove(x);
		this.saveData();
		
	}
	public void editSoba(int id, TipSobe tipSobe, boolean klima, boolean balkon, boolean tv) {
		Soba soba = this.pronadjiSobuPoId(id);
		
		soba.setTipSobe(tipSobe);
		soba.setKlima(klima);
		soba.setBalkon(balkon);
		soba.setTv(tv);
		
		this.saveData();
		
	}
	public void addSoba(TipSobe tipSobe, boolean klima, boolean balkon, boolean tv) {
		int id = sobe.get(sobe.size() - 1).getId() + 1;
		LocalDate today = LocalDate.now();
		ArrayList<LocalDate> slobodniDatumi = new ArrayList<LocalDate>();
		for(int i = 0; i < 365; i ++) {
			slobodniDatumi.add(today);
			today = today.plusDays(1);
		}
		
		Soba soba = new Soba(id, tipSobe, StatusSobe.SLOBODNA,klima, balkon, tv, slobodniDatumi);
		
		this.sobe.add(soba);
		this.saveData();
		
	}
	public void checkInSoba(int id) {
		Soba soba = this.pronadjiSobuPoId(id);
		
		soba.setStatusSobe(StatusSobe.ZAUZETA);

		this.saveData();
		
	}
	public void obrisiDatume(ArrayList<LocalDate> trazeniDatumi, int id) {
		Soba soba = this.pronadjiSobuPoId(id);

		for(LocalDate date : trazeniDatumi) {
			soba.getSlobodniDatumi().remove(date);
		}
		this.saveData();
	}
	public void checkOutSoba(int id) {
		Soba soba = this.pronadjiSobuPoId(id);
		 
		soba.setStatusSobe(StatusSobe.SPREMANJE);
		
		this.saveData();
		
	}
	public void zavrsiSpremanjeSobe(int id) {
		Soba soba = this.pronadjiSobuPoId(id);
		
		soba.setStatusSobe(StatusSobe.SLOBODNA);
		
		this.saveData();
		
	}
	
	
}
