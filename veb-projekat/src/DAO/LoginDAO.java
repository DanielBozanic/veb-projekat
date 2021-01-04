package DAO;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Korisnik;
import beans.Uloga;
import utils.Konstante;
import utils.PomocneFunkcije;

public class LoginDAO {
	
	private ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();

	public LoginDAO() {
		korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		if (korisnici == null)
			korisnici = new ArrayList<Korisnik>();
	}
	
	public Korisnik pronadjiKorisnika(String korisnickoIme, String lozinka) {
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
			} else if (trenutniKorisnik.getUloga() == Uloga.PRODAVAC) {
				linkovi.add(new SimpleEntry<String, String>("manifestacije.html", "Manifestacije"));
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
}
