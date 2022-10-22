package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Cene;
import korisnici.Gost;
import managers.CenovniciManager;
import managers.KorisniciManager;

public class CenovniciManagerTest {

	public static CenovniciManager cenovniciManager = new CenovniciManager("src/cenovnici.txt");

	@BeforeClass
	public  static void setUpBeforeClass() throws Exception{
		cenovniciManager.loadData();
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
//		cenovniciManager.getCenovnici().remove(cenovniciManager.getCenovnici().size() - 1);
		System.out.println("Kraj testa");
	}
	
	@Test
	public void testAddCenovnik() {
		cenovniciManager.addCenovnik(1000, 1000, 1000, 2500, 400, 500, 250, 1000, LocalDate.now(), LocalDate.now().plusDays(30), true);
		
		Cene cenovnik = cenovniciManager.pronadjiCenovnikPoId(cenovniciManager.getCenovnici().get(cenovniciManager.getCenovnici().size() - 1).getId());
		
		assertTrue(cenovnik.getJednokrevetnaSoba() == 1000);
		assertTrue(cenovnik.getPocetniDatum().isEqual(LocalDate.now()));
		
		cenovniciManager.getCenovnici().remove(cenovniciManager.getCenovnici().size() - 1);
		cenovniciManager.saveData();
	}
	
	
	@Test
	public void testCancelCenovnik() {
		Cene cenovnik = new Cene(10000, 1000, 1000, 1000, 2500, 400, 500, 250, 1000, LocalDate.now(), LocalDate.now().plusDays(30), true);
		
		cenovniciManager.getCenovnici().add(cenovnik);
		
		cenovniciManager.cancelCenovnik(10000);
		
		assertFalse(cenovniciManager.getCenovnici().get(cenovniciManager.getCenovnici().size() - 1).isActive());
		
		cenovniciManager.getCenovnici().remove(cenovniciManager.getCenovnici().size() - 1);
		cenovniciManager.saveData();
	}

	
	@Test
	public void testPronadjiCenovnikPoId() {
		Cene cenovnik = new Cene(10000, 1000, 1000, 1000, 2500, 400, 500, 250, 1000, LocalDate.now(), LocalDate.now().plusDays(30), true);
		cenovniciManager.getCenovnici().add(cenovnik);
		
		int id = cenovniciManager.getCenovnici().get(cenovniciManager.getCenovnici().size() - 1).getId();
		
		Cene cenovnik1 = cenovniciManager.pronadjiCenovnikPoId(id);
		
		assertTrue(cenovnik1.getId() == 10000);
		assertTrue(cenovnik1.getJednokrevetnaSoba() == cenovnik.getJednokrevetnaSoba());
				
		cenovniciManager.getCenovnici().remove(cenovniciManager.getCenovnici().size() - 1);
	}
	
	@Test
	public void testLoadData() {
		CenovniciManager cenovniciManager1 = new CenovniciManager("src/cenovnici.txt");
		cenovniciManager1.loadData();
		
		
		assertTrue(cenovniciManager1.getCenovnici() != null);
	}
	
	@Test
	public void testSaveData() {
		CenovniciManager cenovniciManager1 = new CenovniciManager("src/cenovnici.txt");
		cenovniciManager1.loadData();
		int x1 = cenovniciManager1.getCenovnici().size();
		
		
		Cene cenovnik = new Cene(10000, 1000, 1000, 1000, 2500, 400, 500, 250, 1000, LocalDate.now(), LocalDate.now().plusDays(30), true);
		cenovniciManager1.getCenovnici().add(cenovnik);
		
		cenovniciManager1.saveData();
		
		CenovniciManager cenovniciManager2 = new CenovniciManager("src/cenovnici.txt");
		cenovniciManager2.loadData();
		
		int x2 = cenovniciManager2.getCenovnici().size();
		
		assertTrue((x2 - 1) == x1);
		
		cenovniciManager1.getCenovnici().remove(cenovniciManager1.getCenovnici().size() - 1);
		cenovniciManager1.saveData();
	}
	
}
