package DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Karta;
import beans.Korisnik;
import beans.Manifestacija;
import beans.StatusKarte;
import utils.Konstante;
import utils.PomocneFunkcije;

public class KartaDAO {
	
	public KartaDAO() {}
	
	public ArrayList<Karta> getKarte() {
		ArrayList<Karta> karte = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KARTE),
                new TypeReference<ArrayList<Karta>>(){});
		return karte;
	}
	
	public ArrayList<Karta> getRezervisaneKarte() {
		ArrayList<Karta> karte = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KARTE),
                new TypeReference<ArrayList<Karta>>(){});
		ArrayList<Karta> rezervisaneKarte = new ArrayList<Karta>();
		
		for (Karta k : karte) {
			if (k.getStatusKarte().equals(StatusKarte.REZERVISANA)) {
				rezervisaneKarte.add(k);
			}
		}
		
		return rezervisaneKarte;
	}
	
	public ArrayList<Karta> getKarteKupca(String korisnickoIme) {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		ArrayList<Karta> karteKupca = new ArrayList<Karta>();
		
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				karteKupca = k.getSveKarte();
				break;
			}
		}
		
		return karteKupca;
	}
	
	public Collection<Korisnik> getKupciKojiSuRezervisaliKarte(String prodavacKorisnickoIme) {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		ArrayList<Manifestacija> manifestacijeProdavac = new ArrayList<Manifestacija>();
		ArrayList<Karta> kupciKarte = new ArrayList<Karta>();
		HashMap<String, Korisnik> kupci = new HashMap<String, Korisnik>();
		
		for (Korisnik prodavac : korisnici) {
			if (prodavac.getKorisnickoIme().equals(prodavacKorisnickoIme)) {
				manifestacijeProdavac = prodavac.getManifestacije();
				break;
			}
		}
		
		for (Korisnik kupac : korisnici) {
			kupciKarte.addAll(kupac.getSveKarte());
		}
		
		for (Manifestacija m : manifestacijeProdavac) {
			for (Karta karta : kupciKarte) {
				if (m.getNaziv().equals(karta.getManifestacija().getNaziv())) {
					Korisnik kupac = getKupacPoKorisnickomImenu(karta.getKupac());
					if (!kupci.containsKey(kupac.getKorisnickoIme())) {
						kupci.put(kupac.getKorisnickoIme(), kupac);
					}
				}
			}
		}
		
		return kupci.values();
	}
	
	private Korisnik getKupacPoKorisnickomImenu(String korisnickoIme) {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		Korisnik kupac = null;
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				kupac = k;
				break;
			}
		}
		
		return kupac;
	}
}
