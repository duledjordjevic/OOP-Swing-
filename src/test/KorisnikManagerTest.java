package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import korisnici.Gost;
import korisnici.Korisnik;
import korisnici.Recepcioner;
import korisnici.Sobarica;
import korisnici.Zaposleni;
import managers.KorisniciManager;

public class KorisnikManagerTest{
	public static KorisniciManager korisniciManager = new KorisniciManager("src/korisnici.txt");
	
	
	@BeforeClass
	public  static void setUpBeforeClass() throws Exception{
		korisniciManager.loadData();
		korisniciManager.addGost("Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234");
//		String ime, String prezime, String pol, String datumRodjenja, String telefon, String adresa, String username, String lozinka
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		int id = korisniciManager.getKorisnici().get(korisniciManager.getKorisnici().size() - 1).getId();
		korisniciManager.removeGost(id);
	}
	@Test
	public  void testPonadjiKorisnikaPoId() {
		int id = korisniciManager.getKorisnici().get(korisniciManager.getKorisnici().size() - 1).getId();
		
		Korisnik k = korisniciManager.pronadjiKorisnikaPoId(id);
		assertTrue(k.getIme().equals("Marko"));
		assertTrue(k.getPrezime().equals("Markovic"));
		assertTrue(k.getPol().equals("muski"));
		assertTrue(k.getDatum().equals("18.05.2002"));
		assertTrue(k.getAdresa().equals("Kosovska"));
		assertTrue(k.getUsername().equals("m.markovic"));
		assertTrue(k.getLozinka().equals("1234"));
	}
	
	@Test
	public void testAddGost() {
//		Gost gost = new Gost( 10 ,"Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234");
		korisniciManager.addGost("Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234");
		
		int id = korisniciManager.getKorisnici().get(korisniciManager.getKorisnici().size() - 1).getId();
		Korisnik k = korisniciManager.pronadjiKorisnikaPoId(id);
		
		assertTrue(k.getUsername().equals("m.markovic"));
		
//		id = korisniciManager.getGosti().get(korisniciManager.getGosti().size() - 1).getId();
		boolean tf = false;
		for(Gost gost: korisniciManager.getGosti()) {
			if(gost.getUsername().equals("m.markovic")) {
				tf = true;
			}
		}
		assertTrue(tf);
		
		HashMap<String, Gost> gostiHashMap = korisniciManager.getGostiHashMap();
		
		assertTrue(gostiHashMap.containsKey("m.markovic"));
		
		korisniciManager.getKorisnici().remove(korisniciManager.getKorisnici().size() - 1);
		korisniciManager.getGosti().remove(korisniciManager.getGosti().size() - 1);
		korisniciManager.getGostiHashMap().remove(k.getUsername());
		korisniciManager.saveData();
		
	}
	
	@Test
	public void testRemoveGost() {
		Gost gost = new Gost( 1000 ,"Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234");
		korisniciManager.getKorisnici().add(gost);
		korisniciManager.getGosti().add(gost);
		korisniciManager.getGostiHashMap().put("m.markovic", gost);
		
		korisniciManager.removeGost(1000);
		
		assertFalse(korisniciManager.getKorisnici().get(korisniciManager.getKorisnici().size() - 1).getId() == 1000);
		assertFalse(korisniciManager.getGosti().get(korisniciManager.getGosti().size() - 1).getId() == 1000);
		assertFalse(korisniciManager.getGostiHashMap().containsKey("m.markovic"));
		
	}
	
	@Test 
	public void testEditGost() {
//		(int id, String ime, String prezime, String pol, String datumRodjenja, String telefon, String adresa,
//				String username, String lozinka)
		
		Gost gost = new Gost( 1000 ,"Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234");
		
		korisniciManager.getKorisnici().add(gost);
		korisniciManager.editGost(1000, "Nemanja", "Nemanjic", "zenski", "19.05.2000", "123", "BB", "n.nemanjic", "4321");
		
		assertTrue(gost.getIme().equals("Nemanja"));
		assertTrue(gost.getPrezime().equals("Nemanjic"));
		assertTrue(gost.getPol().equals("zenski"));
		assertTrue(gost.getDatum().equals("19.05.2000"));
		assertTrue(gost.getTelefon().equals("123"));
		assertTrue(gost.getAdresa().equals("BB"));
		assertTrue(gost.getUsername().equals("n.nemanjic"));
		assertTrue(gost.getLozinka().equals("4321"));
		
		korisniciManager.getKorisnici().remove(korisniciManager.getKorisnici().size() - 1);
	}
	
	@Test
	public void testAddZaposleni() {
		//Recepcioner
		korisniciManager.addZaposleni("recepcioner", "Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234", 2, 3);
		int id = korisniciManager.getKorisnici().get(korisniciManager.getKorisnici().size() - 1).getId();
		Zaposleni z = (Zaposleni) korisniciManager.pronadjiKorisnikaPoId(id);
		
		assertTrue(z.getUsername().equals("m.markovic"));
		id = korisniciManager.getZaposleni().get(korisniciManager.getZaposleni().size() - 1).getId();
		boolean tf = false;
		for(Zaposleni zaposleni: korisniciManager.getZaposleni()) {
			if(zaposleni.getUsername().equals("m.markovic")) {
				tf = true;
			}
		}
		assertTrue(tf);
		
		
		assertTrue(korisniciManager.getRecepcioneri().containsKey("m.markovic"));
		
		korisniciManager.getKorisnici().remove(korisniciManager.getKorisnici().size() - 1);
		korisniciManager.getZaposleni().remove(korisniciManager.getZaposleni().size() - 1);
		korisniciManager.getRecepcioneri().remove("m.markovic");
		korisniciManager.saveData();
		
		//Sobarica
		korisniciManager.addZaposleni("sobarica", "Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234", 2, 3);
		id = korisniciManager.getKorisnici().get(korisniciManager.getKorisnici().size() - 1).getId();
		z = (Zaposleni) korisniciManager.pronadjiKorisnikaPoId(id);
		
		assertTrue(z.getUsername().equals("m.markovic"));
		id = korisniciManager.getZaposleni().get(korisniciManager.getZaposleni().size() - 1).getId();
		tf = false;
		for(Zaposleni zaposleni: korisniciManager.getZaposleni()) {
			if(zaposleni.getUsername().equals("m.markovic")) {
				tf = true;
			}
		}
		assertTrue(tf);
		
		
		assertTrue(korisniciManager.getSobarice().containsKey("m.markovic"));
		
		korisniciManager.getKorisnici().remove(korisniciManager.getKorisnici().size() - 1);
		korisniciManager.getZaposleni().remove(korisniciManager.getZaposleni().size() - 1);
		korisniciManager.getSobarice().remove("m.markovic");
		korisniciManager.saveData();
	}
	
	@Test
	public void testRemoveZaposleni() {
		// Recepcioner
		Recepcioner recepcioner = new Recepcioner( 1000 ,"Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234", 5, 4);
		korisniciManager.getKorisnici().add(recepcioner);
		korisniciManager.getRecepcioneri().put("m.markovic", recepcioner);
		
		korisniciManager.removeZaposleni(1000);
		
		assertFalse(korisniciManager.getKorisnici().get(korisniciManager.getKorisnici().size() - 1).getId() == 1000);
		assertFalse(korisniciManager.getRecepcioneri().containsKey("m.markovic"));
		
		// Sobarica
		Sobarica sobarica = new Sobarica( 1000 ,"Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234", 5, 4);
		korisniciManager.getKorisnici().add(sobarica);
		korisniciManager.getSobarice().put("m.markovic", sobarica);
		
		korisniciManager.removeZaposleni(1000);
		
		assertFalse(korisniciManager.getKorisnici().get(korisniciManager.getKorisnici().size() - 1).getId() == 1000);
		assertFalse(korisniciManager.getSobarice().containsKey("m.markovic"));
	}
	
	@Test
	public void testEditZaposleni() {
		Recepcioner recepcioner = new Recepcioner( 1000 ,"Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234", 4, 5);
		
		korisniciManager.getKorisnici().add(recepcioner);
		korisniciManager.editZaposleni(1000, "Nemanja", "Nemanjic", "zenski", "19.05.2000", "123", "BB", "n.nemanjic", "4321", 10, 5);
		
		assertTrue(recepcioner.getIme().equals("Nemanja"));
		assertTrue(recepcioner.getPrezime().equals("Nemanjic"));
		assertTrue(recepcioner.getPol().equals("zenski"));
		assertTrue(recepcioner.getDatum().equals("19.05.2000"));
		assertTrue(recepcioner.getTelefon().equals("123"));
		assertTrue(recepcioner.getAdresa().equals("BB"));
		assertTrue(recepcioner.getUsername().equals("n.nemanjic"));
		assertTrue(recepcioner.getLozinka().equals("4321"));
		assertTrue(recepcioner.getNivoStrucneSpreme() == 10);
		assertTrue(recepcioner.getStaz() == 5);
		
		korisniciManager.getKorisnici().remove(korisniciManager.getKorisnici().size() - 1);
		
		
		Sobarica sobarica = new Sobarica( 1000 ,"Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234", 4, 5);
		
		korisniciManager.getKorisnici().add(sobarica);
		korisniciManager.editZaposleni(1000, "Nemanja", "Nemanjic", "zenski", "19.05.2000", "123", "BB", "n.nemanjic", "4321", 10, 5);
		
		assertTrue(sobarica.getIme().equals("Nemanja"));
		assertTrue(sobarica.getPrezime().equals("Nemanjic"));
		assertTrue(sobarica.getPol().equals("zenski"));
		assertTrue(sobarica.getDatum().equals("19.05.2000"));
		assertTrue(sobarica.getTelefon().equals("123"));
		assertTrue(sobarica.getAdresa().equals("BB"));
		assertTrue(sobarica.getUsername().equals("n.nemanjic"));
		assertTrue(sobarica.getLozinka().equals("4321"));
		assertTrue(sobarica.getNivoStrucneSpreme() == 10);
		assertTrue(sobarica.getStaz() == 5);
		
		korisniciManager.getKorisnici().remove(korisniciManager.getKorisnici().size() - 1);
	}
	
	@Test
	public void testLoadData() {
		KorisniciManager korisniciManager1 = new KorisniciManager("src/korisnici.txt");
		korisniciManager1.loadData();
		
		
		assertTrue(korisniciManager1.getKorisnici() != null);
		assertTrue(korisniciManager1.getGosti() != null);
		assertTrue(korisniciManager1.getAdministrator() != null);
		assertTrue(korisniciManager1.getZaposleni() != null);
		assertTrue(korisniciManager1.getRecepcioneri() != null);
		assertTrue(korisniciManager1.getSobarice() != null);
	}
	@Test
	public void testSaveData() {
		KorisniciManager korisniciManager1 = new KorisniciManager("src/korisnici.txt");
		korisniciManager1.loadData();
		int x1 = korisniciManager1.getKorisnici().size();
		
		Gost gost = new Gost( 1000 ,"Marko", "Markovic", "muski", "18.05.2002", "065456789", "Kosovska", "m.markovic", "1234");
		korisniciManager1.getKorisnici().add(gost);
		
		korisniciManager1.saveData();
		
		KorisniciManager korisniciManager2 = new KorisniciManager("src/korisnici.txt");
		korisniciManager2.loadData();
		
		int x2 = korisniciManager2.getKorisnici().size();
		
		assertTrue((x2 - 1) == x1);
		
		korisniciManager1.getKorisnici().remove(korisniciManager1.getKorisnici().size() - 1);
		korisniciManager1.saveData();
		
	}
	
	
	
}
