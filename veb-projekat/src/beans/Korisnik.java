package beans;

import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import utils.CustomPolEnumDeserializer;
import utils.CustomPolEnumSerializer;
import utils.CustomUlogaEnumDeserializer;
import utils.CustomUlogaEnumSerializer;
import utils.LocalDateDeserializer;
import utils.LocalDateSerializer;

public class Korisnik {
	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	@JsonSerialize(using=CustomPolEnumSerializer.class)
	@JsonDeserialize(using=CustomPolEnumDeserializer.class)
	private Pol pol;
	@JsonSerialize(using=LocalDateSerializer.class)
	@JsonDeserialize(using=LocalDateDeserializer.class)
	private LocalDate datumRodjenja;
	@JsonSerialize(using=CustomUlogaEnumSerializer.class)
	@JsonDeserialize(using=CustomUlogaEnumDeserializer.class)
	private Uloga uloga;
	private ArrayList<Karta> sveKarte;
	private ArrayList<Manifestacija> manifestacije;
	private double brojSakupljenihBodova;
	private TipKupca tipKupca;
	
	public Korisnik() {
		
	}
	
	public static void InitKupac(Korisnik kupac)	{
		kupac.uloga = Uloga.KUPAC;
		kupac.brojSakupljenihBodova = 0.0;
		kupac.sveKarte = new ArrayList<Karta>();
		kupac.manifestacije = new ArrayList<Manifestacija>();
		kupac.tipKupca = new TipKupca();
		kupac.tipKupca.setImeTipa(ImeTipa.BRONZANI);
		kupac.tipKupca.setPopust(0.0);
		kupac.tipKupca.setTrazeniBrojBodova(0.0);
	}
	
	public static void InitProdavac(Korisnik prodavac)	{
		prodavac.uloga = Uloga.PRODAVAC;
		prodavac.brojSakupljenihBodova = 0.0;
		prodavac.sveKarte = new ArrayList<Karta>();
		prodavac.manifestacije = new ArrayList<Manifestacija>();
		prodavac.tipKupca = null;
	}
	
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
	
	public TipKupca getTipKupca() {
		return tipKupca;
	}
	
	public void setTipKupaca(TipKupca tipKupca) {
		this.tipKupca = tipKupca;
	}
}
