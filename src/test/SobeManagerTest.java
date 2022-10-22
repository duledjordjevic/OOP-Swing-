package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Cene;
import entity.Soba;
import entity.StatusSobe;
import entity.TipSobe;
import managers.CenovniciManager;
import managers.SobeManager;

public class SobeManagerTest {
	public static SobeManager sobeManager = new SobeManager("src/sobe.txt");
	
	@BeforeClass
	public  static void setUpBeforeClass() throws Exception{
		sobeManager.loadData();
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		System.out.println("Kraj testa");
	}
	
	@Test
	public void testPronadjiSobuPoId() {
//		int id, TipSobe tipSobe, StatusSobe statusSobe,boolean klima, boolean balkon, boolean tv,  ArrayList<LocalDate> datumi
		Soba soba = new Soba(1000, TipSobe.JEDNOKREVETNA, StatusSobe.SLOBODNA, true, false, false, new ArrayList<LocalDate>());
		
		sobeManager.getSobe().add(soba);
		
		Soba soba1 = sobeManager.pronadjiSobuPoId(1000);
		
		assertTrue(soba1.getId() == 1000);
		assertTrue(soba1.getTipSobe().equals(TipSobe.JEDNOKREVETNA));
		
		sobeManager.getSobe().remove(soba);
		
	}
	
	@Test
	public void testRemoveSobu() {
		Soba soba = new Soba(1000, TipSobe.JEDNOKREVETNA, StatusSobe.SLOBODNA, true, false, false, new ArrayList<LocalDate>());
		
		sobeManager.getSobe().add(soba);
		
		
		sobeManager.removeSobu(1000);
		
		assertFalse(sobeManager.getSobe().get(sobeManager.getSobe().size() - 1).getId() == 1000);
		
		sobeManager.getSobe().remove(soba);
	}
	
	@Test
	public void testEditSobu() {
		Soba soba = new Soba(1000, TipSobe.JEDNOKREVETNA, StatusSobe.SLOBODNA, true, false, false, new ArrayList<LocalDate>());
		
		sobeManager.getSobe().add(soba);
		
		sobeManager.editSoba(1000, TipSobe.DVOKREVETNA_SA_DVA_LEZAJA, false, true, true);
		
		assertTrue(soba.getTipSobe() == TipSobe.DVOKREVETNA_SA_DVA_LEZAJA);
		assertFalse(soba.isKlima());
		assertTrue(soba.isBalkon());
		assertTrue(soba.isTv());
		
		sobeManager.getSobe().remove(soba);
	}
	
	@Test
	public void testAddSobu() {
		int x1 = sobeManager.getSobe().size();
		sobeManager.addSoba(TipSobe.TROKREVETNA, false, false, false);
		int x2 = sobeManager.getSobe().size();
		
		assertTrue((x2 - 1) ==  x1);
		int index = sobeManager.getSobe().size() - 1;
		assertTrue(sobeManager.getSobe().get(index).getTipSobe().equals(TipSobe.TROKREVETNA));
		assertFalse(sobeManager.getSobe().get(index).isBalkon());
		assertFalse(sobeManager.getSobe().get(index).isKlima());
		assertFalse(sobeManager.getSobe().get(index).isTv());
		
		sobeManager.getSobe().remove(index);
	}
	
	@Test
	public void testCheckInSoba() {
		Soba soba = new Soba(1000, TipSobe.JEDNOKREVETNA, StatusSobe.SLOBODNA, true, false, false, new ArrayList<LocalDate>());
		
		sobeManager.getSobe().add(soba);
		
		sobeManager.checkInSoba(1000);
		
		assertTrue(soba.getStatusSobe().equals(StatusSobe.ZAUZETA));
		
		sobeManager.getSobe().remove(soba);
		
	}
	
	@Test
	public void testCheckOutSoba() {
		Soba soba = new Soba(1000, TipSobe.JEDNOKREVETNA, StatusSobe.ZAUZETA, true, false, false, new ArrayList<LocalDate>());
		
		sobeManager.getSobe().add(soba);
		
		sobeManager.checkOutSoba(1000);
		
		assertTrue(soba.getStatusSobe().equals(StatusSobe.SPREMANJE));
		
		sobeManager.getSobe().remove(soba);
		
	}
	
	@Test
	public void testZavrsiSpremanjeSobe() {
		Soba soba = new Soba(1000, TipSobe.JEDNOKREVETNA, StatusSobe.SPREMANJE, true, false, false, new ArrayList<LocalDate>());
		
		sobeManager.getSobe().add(soba);
		
		sobeManager.zavrsiSpremanjeSobe(1000);
		
		assertTrue(soba.getStatusSobe().equals(StatusSobe.SLOBODNA));
		
		sobeManager.getSobe().remove(soba);
		
	}
	
	@Test
	public void testObrisiDatume() {
		ArrayList<LocalDate> datumi = new ArrayList<LocalDate>();
		datumi.add(LocalDate.now());
		datumi.add(LocalDate.now().plusDays(1));
		datumi.add(LocalDate.now().plusDays(2));
		datumi.add(LocalDate.now().plusDays(3));
		
		Soba soba = new Soba(1000, TipSobe.JEDNOKREVETNA, StatusSobe.SPREMANJE, true, false, false, datumi);
		sobeManager.getSobe().add(soba);
		
		ArrayList<LocalDate> datumiZaBrisanje = new ArrayList<LocalDate>();
		datumiZaBrisanje.add(LocalDate.now());
		
		sobeManager.obrisiDatume(datumiZaBrisanje, 1000);
		
		ArrayList<LocalDate> datumi2 = new ArrayList<LocalDate>();
		datumi2.add(LocalDate.now().plusDays(1));
		datumi2.add(LocalDate.now().plusDays(2));
		datumi2.add(LocalDate.now().plusDays(3));
		
		boolean tf = true;
		for(LocalDate date: sobeManager.getSobe().get(sobeManager.getSobe().size() - 1).getSlobodniDatumi()) {
			if(!datumi2.contains(date)) {
				tf = false;
			}
		}
		
		assertTrue(tf);
		
		sobeManager.getSobe().remove(soba);
	}
	
	@Test
	public void testLoadData() {
		SobeManager sobeManager1 = new SobeManager("src/sobe.txt");
		sobeManager1.loadData();
		
		
		assertTrue(sobeManager1.getSobe() != null);
	}
	
	@Test
	public void testSaveData() {
		SobeManager sobeManager1 = new SobeManager("src/sobe.txt");
		sobeManager1.loadData();
		int x1 = sobeManager1.getSobe().size();
		
		
		Soba soba = new Soba(1000, TipSobe.JEDNOKREVETNA, StatusSobe.SPREMANJE, true, false, false, new ArrayList<LocalDate>());
		sobeManager1.getSobe().add(soba);
		
		sobeManager1.saveData();
		
		SobeManager sobeManager2 = new SobeManager("src/sobe.txt");
		sobeManager2.loadData();
		
		int x2 = sobeManager2.getSobe().size();
		
		assertTrue((x2 - 1) == x1);
		
		sobeManager1.getSobe().remove(sobeManager1.getSobe().size() - 1);
		sobeManager1.saveData();
	}
	
	
}
