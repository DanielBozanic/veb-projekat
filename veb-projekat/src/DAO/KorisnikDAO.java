package DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.AbstractMap.SimpleEntry;


import com.fasterxml.jackson.core.type.TypeReference;

import beans.Korisnik;
import beans.Uloga;
import utils.Konstante;
import utils.PomocneFunkcije;

public class KorisnikDAO {
	
	public KorisnikDAO() {}
	
	public ArrayList<SimpleEntry<String, String>> getLinkovi(Korisnik trenutniKorisnik){
		ArrayList<SimpleEntry<String, String>> linkovi = new ArrayList<SimpleEntry<String, String>>();
		if (trenutniKorisnik != null) {
			linkovi.add(new SimpleEntry<String, String>("profil.html", "Profil"));
			if (trenutniKorisnik.getUloga() == Uloga.ADMINISTRATOR) {
				linkovi.add(new SimpleEntry<String, String>("dodavanjeNovogProdavca.html", "Dodaj novog prodavca"));
				linkovi.add(new SimpleEntry<String, String>("radSaManifestacijamaAdmin.html", "Rad sa manifestacijama"));
				linkovi.add(new SimpleEntry<String, String>("pregledSvihKorisnika.html", "Svi korisnici"));
				linkovi.add(new SimpleEntry<String, String>("pregledSvihKarti.html", "Sve karte"));
			} else if (trenutniKorisnik.getUloga() == Uloga.PRODAVAC) {
				linkovi.add(new SimpleEntry<String, String>("dodajManifestacije.html", "Dodavanje manifestacije"));
				linkovi.add(new SimpleEntry<String, String>("izmenaManifestacije.html", "Izmena manifestacije"));
				linkovi.add(new SimpleEntry<String, String>("odobrenjeKomentara.html", "Odobri komentare"));
				linkovi.add(new SimpleEntry<String, String>("pregledManifestacijaProdavac.html", "Pregled manifestacija"));
				linkovi.add(new SimpleEntry<String, String>("pregledRezervisanihKarti.html", "Pregled svih rezervisanih karti"));
				linkovi.add(new SimpleEntry<String, String>("pregledKupacaKojiSuRezervisaliKarte.html", "Kupci sa rezervisanim kartama"));
			} else {
				linkovi.add(new SimpleEntry<String, String>("rezervacijaKarte.html", "Rezervacija karti"));
				linkovi.add(new SimpleEntry<String, String>("odustanakRezervacije.html", "Odustanak rezervacije"));
				linkovi.add(new SimpleEntry<String, String>("pregledKartiKupca.html", "Pregled karti"));
			}
		}
		return linkovi;
	}
	
	public ArrayList<Korisnik> getKorisnici() {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		return korisnici;
	}
	
	public ArrayList<Korisnik> getSortiraneKorisnike(String kriterijumSortiranja, String kriterijumSortiranja2) {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		if (kriterijumSortiranja2.equals("opadajuce")) {
			sortOpadajuce(korisnici, kriterijumSortiranja);
		} else if (kriterijumSortiranja2.equals("rastuce")) {
			sortRastuce(korisnici, kriterijumSortiranja);
		}
		return korisnici;
	}
	
	private static void sortRastuce(ArrayList<Korisnik> korisnici, String kriterijumSortiranja) {
		if (kriterijumSortiranja.equals("korisnickoIme")) {
			Collections.sort(korisnici, new Comparator<Korisnik>() {
				public int compare(Korisnik k1, Korisnik k2) {
					return k1.getKorisnickoIme().compareTo(k2.getKorisnickoIme());
				}
			});
		} else if (kriterijumSortiranja.equals("ime")) {
			Collections.sort(korisnici, new Comparator<Korisnik>() {
				public int compare(Korisnik k1, Korisnik k2) {
					return k1.getIme().compareTo(k2.getIme());
				}
			});
		} else if (kriterijumSortiranja.equals("prezime")) {
			Collections.sort(korisnici, new Comparator<Korisnik>() {
				public int compare(Korisnik k1, Korisnik k2) {
					return k1.getPrezime().compareTo(k2.getPrezime());
				}
			});
		} else if (kriterijumSortiranja.equals("brojSakupljenihBodova")) {
			Collections.sort(korisnici, new Comparator<Korisnik>() {
				public int compare(Korisnik k1, Korisnik k2) {
					return (int) (k1.getBrojSakupljenihBodova() - k2.getBrojSakupljenihBodova());
				}
			});
		}
	}
	
	private void sortOpadajuce(ArrayList<Korisnik> korisnici, String kriterijumSortiranja) {
		if (kriterijumSortiranja.equals("korisnickoIme")) {
			Collections.sort(korisnici, new Comparator<Korisnik>() {
				public int compare(Korisnik k1, Korisnik k2) {
					return k2.getKorisnickoIme().compareTo(k1.getKorisnickoIme());
				}
			});
		} else if (kriterijumSortiranja.equals("ime")) {
			Collections.sort(korisnici, new Comparator<Korisnik>() {
				public int compare(Korisnik k1, Korisnik k2) {
					return k2.getIme().compareTo(k1.getIme());
				}
			});
		} else if (kriterijumSortiranja.equals("prezime")) {
			Collections.sort(korisnici, new Comparator<Korisnik>() {
				public int compare(Korisnik k1, Korisnik k2) {
					return k2.getPrezime().compareTo(k1.getPrezime());
				}
			});
		} else if (kriterijumSortiranja.equals("brojSakupljenihBodova")) {
			Collections.sort(korisnici, new Comparator<Korisnik>() {
				public int compare(Korisnik k1, Korisnik k2) {
					return (int) (k2.getBrojSakupljenihBodova() - k1.getBrojSakupljenihBodova());
				}
			});
		}
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
	
	public Korisnik izmenaPodatakaTrenutnogKorisnika(Korisnik korisnik) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		Korisnik izmenjenKorisnik = null;
		
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnik.getKorisnickoIme())) {
				k.setLozinka(korisnik.getLozinka());
				k.setIme(korisnik.getIme());
				k.setPrezime(korisnik.getPrezime());
				k.setDatumRodjenja(korisnik.getDatumRodjenja());
				k.setPol(korisnik.getPol());
				izmenjenKorisnik = k;
				break;
			}
		}
		
		if (izmenjenKorisnik != null) {
			PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
		}
		
		return izmenjenKorisnik;
	}

}
