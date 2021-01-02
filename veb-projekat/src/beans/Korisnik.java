package beans;

import java.time.LocalDate;
import java.util.ArrayList;

public class Korisnik {
	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	private Pol pol;
	private LocalDate datumRodjenja;
	private Uloga uloga;
	private ArrayList<Karta> sveKarte;
	private ArrayList<Manifestacija> manifestacije;
	private double brojSakupljenihBodova;
	private TipKupaca tipKupaca;
	
	public Korisnik() {}
	
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	
	public String getLozinka() {
		return lozinka;
	}
	
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public String getPrezime() {
		return prezime;
	}
	
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	public Pol getPol() {
		return pol;
	}
	
	public void setPol(Pol pol) {
		this.pol = pol;
	}
	
	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}
	
	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
	
	public Uloga getUloga() {
		return uloga;
	}
	
	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	
	public ArrayList<Karta> getSveKarte() {
		return sveKarte;
	}
	
	public void setSveKarte(ArrayList<Karta> sveKarte) {
		this.sveKarte = sveKarte;
	}
	
	public ArrayList<Manifestacija> getManifestacije() {
		return manifestacije;
	}
	
	public void setManifestacije(ArrayList<Manifestacija> manifestacije) {
		this.manifestacije = manifestacije;
	}
	
	public double getBrojSakupljenihBodova() {
		return brojSakupljenihBodova;
	}
	
	public void setBrojSakupljenihBodova(double brojSakupljenihBodova) {
		this.brojSakupljenihBodova = brojSakupljenihBodova;
	}
	
	public TipKupaca getTipKupaca() {
		return tipKupaca;
	}
	
	public void setTipKupaca(TipKupaca tipKupaca) {
		this.tipKupaca = tipKupaca;
	}
}
