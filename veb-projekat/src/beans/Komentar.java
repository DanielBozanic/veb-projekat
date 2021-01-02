package beans;

public class Komentar {
	private Korisnik kupacKarte;
	private Manifestacija manifestacija;
	private String tekstKomentara;
	private int ocena;
	
	public Komentar() {}

	public Korisnik getKupacKarte() {
		return kupacKarte;
	}

	public void setKupacKarte(Korisnik kupacKarte) {
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
}
