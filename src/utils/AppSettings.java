package utils;

public class AppSettings {
	private String korisniciFileName, rezervacijeFileName ,sobeFileName, cenovniciFileName;
	private String rezervacijeIzvestajiFileName;
	
	public AppSettings(String korisniciFileName, String rezervacijeFileName,String rezervacijeIzvestajiFileName, String sobeFileName, String cenovniciFileName) {
		super();
		this.korisniciFileName = korisniciFileName;
		this.rezervacijeFileName = rezervacijeFileName;
		this.rezervacijeIzvestajiFileName = rezervacijeIzvestajiFileName;
		this.sobeFileName = sobeFileName;
		this.cenovniciFileName = cenovniciFileName;
		
	}


	public String getCenovniciFileName() {
		return cenovniciFileName;
	}


	public void setCenovniciFileName(String cenovniciFileName) {
		this.cenovniciFileName = cenovniciFileName;
	}


	public String getKorisniciFileName() {
		return korisniciFileName;
	}


	public void setKorisniciFileName(String korisniciFileName) {
		this.korisniciFileName = korisniciFileName;
	}


	public String getRezervacijeFileName() {
		return rezervacijeFileName;
	}


	public void setRezervacijeFileName(String rezervacijeFileName) {
		this.rezervacijeFileName = rezervacijeFileName;
	}


	public String getSobeFileName() {
		return sobeFileName;
	}


	public void setSobeFileName(String sobeFileName) {
		this.sobeFileName = sobeFileName;
	}


	public String getRezervacijeIzvestajiFileName() {
		return rezervacijeIzvestajiFileName;
	}


	public void setRezervacijeIzvestajiFileName(String rezervacijeIzvestajiFileName) {
		this.rezervacijeIzvestajiFileName = rezervacijeIzvestajiFileName;
	}

	
	
	
}
