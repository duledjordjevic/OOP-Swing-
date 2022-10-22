package test;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.IzvestajRezervacija;
import entity.Rezervacija;
import entity.Soba;
import entity.StatusRezervacije;
import entity.StatusSobe;
import entity.TipSobe;
import managers.RezervacijeManager;
import managers.SobeManager;

public class RezervacijeManagerTest {
	public static RezervacijeManager rezervacijeManager = new RezervacijeManager("src/rezervacije.txt", "src/rezervacijeIzvestaj.txt");
	
	@BeforeClass
	public static void setUpBeforeClass() {
		rezervacijeManager.loadData();
	}
	
	@AfterClass
	public static void tearDownAdterClass() {
		System.out.println("Kraj testa");
	}
	
	@Test
	public void testLoadData() {
		RezervacijeManager rezervacijeManager1 = new RezervacijeManager("src/rezervacije.txt", "src/rezervacijeIzvestaj.txt");
		rezervacijeManager1.loadData();
		
		
		assertTrue(rezervacijeManager1.getRezervacije() != null);
	}
	

	@Test
	public void testSaveData() {
		RezervacijeManager rezervacijeManager1 = new RezervacijeManager("src/rezervacije.txt", "src/rezervacijeIzvestaj.txt");
		rezervacijeManager1.loadData();
		int x1 = rezervacijeManager1.getRezervacije().size();
		int y1 = rezervacijeManager1.getIzvestajiRezervacija().size();
		
//		int id, String username, TipSobe tipSobe, LocalDate pocetniDatum, LocalDate krajnjiDatum, StatusRezervacije statusRezervacije, int cena, int idSobe, String sobaricaIme
		Rezervacija rez = new Rezervacija(1000, "m.markovic" , TipSobe.JEDNOKREVETNA, LocalDate.now(),LocalDate.now().plusDays(1), StatusRezervacije.NA_CEKANJU, 1000, 3, "/");
		rezervacijeManager1.getRezervacije().add(rez);
		
//		int id, int idRezervacije, LocalDate datum, StatusRezervacije statusRezervacije
		IzvestajRezervacija izvRez = new IzvestajRezervacija(9999, 1000, LocalDate.now(), StatusRezervacije.NA_CEKANJU);
		rezervacijeManager1.getIzvestajiRezervacija().add(izvRez);
		
		rezervacijeManager1.saveData();
		
		RezervacijeManager rezervacijeManager2 = new RezervacijeManager("src/rezervacije.txt", "src/rezervacijeIzvestaj.txt");
		rezervacijeManager2.loadData();
		
		int x2 = rezervacijeManager2.getRezervacije().size();
		int y2 = rezervacijeManager2.getIzvestajiRezervacija().size();
		
		assertTrue((x2 - 1) == x1);
		assertTrue((y2- 1) == y1);
		
		rezervacijeManager1.getRezervacije().remove(rezervacijeManager1.getRezervacije().size() - 1);
		rezervacijeManager1.getIzvestajiRezervacija().remove(rezervacijeManager1.getIzvestajiRezervacija().size() - 1);
		rezervacijeManager1.saveData();
	}
	
	@Test 
	public void testAddRezervacija() {
//		String username, SobeManager sobeManager, Soba soba, TipSobe tipSobe, LocalDate pocetniDatum, LocalDate krajnjiDatum, int cena, ArrayList<LocalDate> trazeniDatumi
		SobeManager sobeManager = new SobeManager("src/sobe.txt");
		sobeManager.loadData();
//		Soba soba = new Soba(1000, TipSobe.JEDNOKREVETNA, StatusSobe.SPREMANJE, true, false, false, new ArrayList<LocalDate>());
		sobeManager.addSoba(TipSobe.JEDNOKREVETNA, false, false, false);
		Soba soba = sobeManager.pronadjiSobuPoId(sobeManager.getSobe().get(sobeManager.getSobe().size() - 1).getId());
		
		ArrayList<LocalDate> datumi = new ArrayList<LocalDate>();
		datumi.add(LocalDate.now());
		datumi.add(LocalDate.now().plusDays(1));
		datumi.add(LocalDate.now().plusDays(2));

		rezervacijeManager.addRezervacija("m.markovic", sobeManager, soba, TipSobe.JEDNOKREVETNA, LocalDate.now(), LocalDate.now().plusDays(2), 1000, datumi);
		
		int indexRez = rezervacijeManager.getRezervacije().size() -1;
		int indexIzvRez = rezervacijeManager.getIzvestajiRezervacija().size() -1;
		
		assertTrue(rezervacijeManager.getRezervacije().get(indexRez).getIdSobe() == soba.getId());
		assertTrue(rezervacijeManager.getRezervacije().get(indexRez).getPocetniDatum().isEqual(LocalDate.now()));
		assertTrue(rezervacijeManager.getIzvestajiRezervacija().get(indexIzvRez).getIdRezervacije() == rezervacijeManager.getRezervacije().get(indexRez).getId());
		
		sobeManager.getSobe().remove(sobeManager.getSobe().size() - 1);
		sobeManager.saveData();
		rezervacijeManager.getRezervacije().remove(indexRez);
		rezervacijeManager.getIzvestajiRezervacija().remove(indexIzvRez);
		rezervacijeManager.saveData();
	}
	
	@Test
	public void testAddIzvestajRez() {
		Rezervacija rez = new Rezervacija(1000, "m.markovic" , TipSobe.JEDNOKREVETNA, LocalDate.now(),LocalDate.now().plusDays(1), StatusRezervacije.NA_CEKANJU, 1000, 3, "/");
		rezervacijeManager.getRezervacije().add(rez);
		
		rezervacijeManager.addIzvestajRezervacija(1000, StatusRezervacije.NA_CEKANJU);
		
		int indexRez = rezervacijeManager.getRezervacije().size() -1;
		int indexIzvRez = rezervacijeManager.getIzvestajiRezervacija().size() -1;
		assertTrue(rezervacijeManager.getIzvestajiRezervacija().get(indexIzvRez).getIdRezervacije() == 1000);
		
		rezervacijeManager.getRezervacije().remove(indexRez);
		rezervacijeManager.getIzvestajiRezervacija().remove(indexIzvRez);
		rezervacijeManager.saveData();
		
	}
	
	@Test
	public void testOtkaziRezervaciju() {
		Rezervacija rez = new Rezervacija(1000, "m.markovic" , TipSobe.JEDNOKREVETNA, LocalDate.now(),LocalDate.now().plusDays(1), StatusRezervacije.NA_CEKANJU, 1000, 3, "/");
		rezervacijeManager.getRezervacije().add(rez);
		
		
		rezervacijeManager.otkaziRezervaciju(1000);
		
		int indexRez = rezervacijeManager.getRezervacije().size() -1;
		int indexIzvRez = rezervacijeManager.getIzvestajiRezervacija().size() -1;
		
		assertTrue(rezervacijeManager.getRezervacije().get(indexRez).getStatusRezervacije().equals(StatusRezervacije.OTKAZANA));
		
		rezervacijeManager.getRezervacije().remove(indexRez);
		rezervacijeManager.getIzvestajiRezervacija().remove(indexIzvRez);
		rezervacijeManager.saveData();
	}
	
	@Test
	public void testCancelRezervaciju() {
		Rezervacija rez = new Rezervacija(1000, "m.markovic" , TipSobe.JEDNOKREVETNA, LocalDate.now(),LocalDate.now().plusDays(1), StatusRezervacije.NA_CEKANJU, 1000, 3, "/");
		rezervacijeManager.getRezervacije().add(rez);
		
		
		rezervacijeManager.cancelRezervaciju(1000);
		
		int indexRez = rezervacijeManager.getRezervacije().size() -1;
		int indexIzvRez = rezervacijeManager.getIzvestajiRezervacija().size() -1;
		
		assertTrue(rezervacijeManager.getRezervacije().get(indexRez).getStatusRezervacije().equals(StatusRezervacije.ODBIJENA));
		
		rezervacijeManager.getRezervacije().remove(indexRez);
		rezervacijeManager.getIzvestajiRezervacija().remove(indexIzvRez);
		rezervacijeManager.saveData();
	}
	
	@Test
	public void testCheckedRezervaciju() {
		Rezervacija rez = new Rezervacija(1000, "m.markovic" , TipSobe.JEDNOKREVETNA, LocalDate.now(),LocalDate.now().plusDays(1), StatusRezervacije.NA_CEKANJU, 1000, 3, "/");
		rezervacijeManager.getRezervacije().add(rez);
		
		
		rezervacijeManager.checkedRezervaciju(1000);
		
		int indexRez = rezervacijeManager.getRezervacije().size() -1;
		int indexIzvRez = rezervacijeManager.getIzvestajiRezervacija().size() -1;
		
		assertTrue(rezervacijeManager.getRezervacije().get(indexRez).getStatusRezervacije().equals(StatusRezervacije.POTVRDJENA));
		
		rezervacijeManager.getRezervacije().remove(indexRez);
		rezervacijeManager.getIzvestajiRezervacija().remove(indexIzvRez);
		rezervacijeManager.saveData();
	}
	
}
