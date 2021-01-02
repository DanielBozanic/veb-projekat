package beans;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Karta {
	private String identifikatorKarte;
	private Manifestacija manifestacija;
	private LocalDateTime datumIVremeManifestacije;
	private BigDecimal cena;
	private Korisnik kupac;
	private StatusKarte statusKarte;
	private TipKarte tipKarte;
	
	public Karta() {}

	public String getIdentifikatorKarte() {
		return identifikatorKarte;
	}

	public void setIdentifikatorKarte(String identifikatorKarte) {
		this.identifikatorKarte = identifikatorKarte;
	}

	public Manifestacija getManifestacija() {
		return manifestacija;
	}

	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}

	public LocalDateTime getDatumIVremeManifestacije() {
		return datumIVremeManifestacije;
	}

	public void setDatumIVremeManifestacije(LocalDateTime datumIVremeManifestacije) {
		this.datumIVremeManifestacije = datumIVremeManifestacije;
	}

	public BigDecimal getCena() {
		return cena;
	}

	public void setCena(BigDecimal cena) {
		this.cena = cena;
	}

	public Korisnik getKupac() {
		return kupac;
	}

	public void setKupac(Korisnik kupac) {
		this.kupac = kupac;
	}

	public StatusKarte getStatusKarte() {
		return statusKarte;
	}

	public void setStatusKarte(StatusKarte statusKarte) {
		this.statusKarte = statusKarte;
	}

	public TipKarte getTipKarte() {
		return tipKarte;
	}

	public void setTipKarte(TipKarte tipKarte) {
		this.tipKarte = tipKarte;
	}
}
