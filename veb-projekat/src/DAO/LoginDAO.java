package DAO;

import java.util.AbstractMap.SimpleEntry;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.util.ArrayList;

import beans.Korisnik;
import beans.Uloga;
import utils.Konstante;
import utils.PomocneFunkcije;

public class LoginDAO {
	
	public LoginDAO() {
		
	}
	
	public Korisnik pronadjiKorisnika(String korisnickoIme, String lozinka) {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		Korisnik korisnik = null;
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme) && k.getLozinka().equals(lozinka)) {
				korisnik = k;
				break;
			}
		}
		return korisnik;
	}
	
	public ArrayList<SimpleEntry<String, String>> getLinkovi(Korisnik trenutniKorisnik){
		ArrayList<SimpleEntry<String, String>> linkovi = new ArrayList<SimpleEntry<String, String>>();
		if (trenutniKorisnik != null) {
			linkovi.add(new SimpleEntry<String, String>("profil.html", "Profil"));
			if (trenutniKorisnik.getUloga() == Uloga.ADMINISTRATOR) {
				linkovi.add(new SimpleEntry<String, String>("pregledSvihKorisnika.html", "Svi korisnici"));
				linkovi.add(new SimpleEntry<String, String>("prikazSvihKarata.html", "Sve karte"));
				linkovi.add(new SimpleEntry<String, String>("dodavanjeNovogProdavca.html", "Dodaj novog prodavca"));
				linkovi.add(new SimpleEntry<String, String>("odobrenjeManifestacije.html", "Odobrenje manifestacije"));
			} else if (trenutniKorisnik.getUloga() == Uloga.PRODAVAC) {
				linkovi.add(new SimpleEntry<String, String>("dodajManifestacije.html", "Dodavanje manifestacije"));
				linkovi.add(new SimpleEntry<String, String>("izmenaManifestacije.html", "Izmena manifestacije"));
				linkovi.add(new SimpleEntry<String, String>("pregledManifestacija.html", "Pregled manifestacija"));
				linkovi.add(new SimpleEntry<String, String>("pregledRezervisanihKarata.html", "Pregled rezervisanih karti"));
				linkovi.add(new SimpleEntry<String, String>("pregledKupacaKojiSuRezervisaliKarte.html", "Kupci sa rezervisanim kartama"));
				linkovi.add(new SimpleEntry<String, String>("prikazSvihRezervisanihKarata.html", "Prikaz svih rezervisanih karti"));
			} else {
				linkovi.add(new SimpleEntry<String, String>("pregledKarata.html", "Pregled karti"));
				linkovi.add(new SimpleEntry<String, String>("rezervacijaKarte.html", "Rezervacija karti"));
			}
		}
		return linkovi;
	}
	
	public ArrayList<Korisnik> getKorisnici() {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		return korisnici;
	}
	
	public Korisnik getPodaciTrenutniKorisnik(String korisnickoIme) {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		Korisnik korisnik = null;
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				korisnik = k;
				break;
			}
		}
		return korisnik;
	}
}
