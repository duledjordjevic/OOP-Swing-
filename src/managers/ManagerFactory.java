package managers;

import utils.AppSettings;

public class ManagerFactory {
	private AppSettings appSettings;
	private KorisniciManager korisniciManager;
	private RezervacijeManager rezervacijeManager;
	private SobeManager sobeManager;
	private CenovniciManager cenovniciManager;
	
	public ManagerFactory(AppSettings appSettings) {
		this.appSettings = appSettings;
		this.korisniciManager = new KorisniciManager(this.appSettings.getKorisniciFileName());
		this.rezervacijeManager = new RezervacijeManager(this.appSettings.getRezervacijeFileName(), this.appSettings.getRezervacijeIzvestajiFileName());
		this.sobeManager = new SobeManager(this.appSettings.getSobeFileName());
		this.cenovniciManager = new CenovniciManager(this.appSettings.getCenovniciFileName());
	}

	public KorisniciManager getKorisniciManager() {
		return korisniciManager;
	}

	public RezervacijeManager getRezervacijeManager() {
		return rezervacijeManager;
	}


	public SobeManager getSobeManager() {
		return sobeManager;
	}


	public CenovniciManager getCenovniciManager() {
		return cenovniciManager;
	}


	public void loadData() {
		this.korisniciManager.loadData();
		this.rezervacijeManager.loadData();	
		this.sobeManager.loadData();
		this.cenovniciManager.loadData();
		this.rezervacijeManager.odbijanjeRezervacija();
	}
	
}
