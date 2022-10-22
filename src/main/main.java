package main;
import gui.MainFrame;
import managers.ManagerFactory;
import utils.AppSettings;

public class main {

	public static void main(String[] args) {
		

		AppSettings appSettings = new AppSettings("src/korisnici.txt", "src/rezervacije.txt", "src/rezervacijeIzvestaj.txt","src/sobe.txt", "src/cenovnici.txt");

		ManagerFactory controlers = new ManagerFactory(appSettings);

		controlers.loadData();


		MainFrame main = new MainFrame(controlers);
		
//		
		
	}

}
