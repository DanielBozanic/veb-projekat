package beans;

public class Komentar {
	private String idKomentara;
	private String kupacKarte;
	private Manifestacija manifestacija;
	private String tekstKomentara;
	private int ocena;
	private boolean odobren;
	
	public Komentar() {}
	
	public String getIdKomentara() {
		return idKomentara;
	}
	
	public void setIdKomentara(String idKomentara) {
		this.idKomentara = idKomentara;
	}
	
	public String getKupacKarte() {
		return kupacKarte;
	}

	public void setKupacKarte(String kupacKarte) {
		this.kupacKarte = kupacKarte;
	}

	public Manifestacija getManifestacija() {
		return manifestacija;
	}

	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}

	public String getTekstKomentara() {
		return tekstKomentara;
	}

	public void setTekstKomentara(String tekstKomentara) {
		this.tekstKomentara = tekstKomentara;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public boolean isOdobren() {
		return odobren;
	}

	public void setOdobren(boolean odobren) {
		this.odobren = odobren;
	}
}
