package entity;

public class Plata {
	private int ukupnaPlata;
	private static int minimalac = 35000;
	
	public Plata() {
		
	}
	public Plata(int nivoStrucneSpreme, int staz) {
		super();
		this.ukupnaPlata = minimalac + nivoStrucneSpreme * 1500 + staz * 1000;
	}
	public int getUkupnaPlata() {
		return ukupnaPlata;
	}
	public void setUkupnaPlata(int ukupnaPlata) {
		this.ukupnaPlata = ukupnaPlata;
	}
	public static int getMinimalac() {
		return minimalac;
	}
	public static void setMinimalac(int minimalac) {
		Plata.minimalac = minimalac;
	}

	
}
