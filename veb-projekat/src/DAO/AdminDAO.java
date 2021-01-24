package DAO;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Karta;
import beans.Korisnik;
import beans.Manifestacija;
import beans.StatusKarte;
import beans.Uloga;
import utils.Konstante;
import utils.PomocneFunkcije;

public class AdminDAO {
	
	public AdminDAO() {}
	
	public String dodajProdavca(Korisnik prodavac) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KORISNICI), new TypeReference<ArrayList<Korisnik>>(){});
		boolean valid = true;
		String povratnaPoruka = "";
		for (Korisnik k : korisnici) {
    		if (k.getKorisnickoIme().equals(prodavac.getKorisnickoIme())) {
    			valid = false;
    			povratnaPoruka = "Prodavac sa ovim korisnickim imenom vec postoji!";
    			break;
    		}
    	}
		if (valid) {
			korisnici.add(prodavac);
			PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
			povratnaPoruka = "Uspesno dodat novi prodavac.";
		}
		return povratnaPoruka;
	}
	
	public ArrayList<Manifestacija> promeniStatusManifestacije(String naziv) throws IOException {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		boolean valid = false;
		for (Manifestacija manifestacija : manifestacije) {
			if (manifestacija.getNaziv().equals(naziv)) {
				manifestacija.setAktivan(true);
				valid = true;
				azurirajManifestacijeZaProdavca(manifestacija);
				break;
			}
		}
		
		if (valid) {
			PomocneFunkcije.upisi(manifestacije, Konstante.FAJL_MANIFESTACIJE);
		}
		return manifestacije;
	}
	
	private void azurirajManifestacijeZaProdavca(Manifestacija manifestacija) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		for (Korisnik k : korisnici) {
			if (!k.getManifestacije().isEmpty()) {
				for (Manifestacija m : k.getManifestacije()) {
					if (m.getNaziv().equals(manifestacija.getNaziv())) {
						m.setAktivan(manifestacija.isAktivan());
						PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
						return;
					}
				}
			}
		}
	}
	
	public ArrayList<Korisnik> obrisiKorisnika(String korisnickoIme) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				k.setObrisan(true);
				PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
				return korisnici;
			}
		}
		return null;
	}
	
	public ArrayList<Korisnik> blokirajKorisnika(String korisnickoIme) throws IOException {
		return blokirajOdblokiraj(korisnickoIme, true);
	}
	
	public ArrayList<Korisnik> odblokirajKorisnika(String korisnickoIme) throws IOException {
		return blokirajOdblokiraj(korisnickoIme, false);
	}
	
	private ArrayList<Korisnik> blokirajOdblokiraj(String korisnickoIme, boolean blokiraj) 
			throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				k.setBlokiran(blokiraj);
				PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
				break;
			}
		}
		return korisnici;
	}
	
	public ArrayList<Korisnik> blokirajSumnjivogKupca(String korisnickoIme) throws IOException {
		return blokirajOdblokirajSumnjivogKupca(korisnickoIme, true);
	}
	
	public ArrayList<Korisnik> odblokirajSumnjivogKupca(String korisnickoIme) throws IOException {
		return blokirajOdblokirajSumnjivogKupca(korisnickoIme, false);
	}
	
	private ArrayList<Korisnik> blokirajOdblokirajSumnjivogKupca(String korisnickoIme, boolean blokiraj) throws IOException {
		ArrayList<Korisnik> sumnjiviKupci = getSumnjiviKupci();
		blokirajOdblokiraj(korisnickoIme, blokiraj);
		for (Korisnik kupac : sumnjiviKupci) {
			if (kupac.getKorisnickoIme().equals(korisnickoIme)) {
				kupac.setBlokiran(blokiraj);
				break;
			}
		}
		return sumnjiviKupci;
	}
	
	public ArrayList<Korisnik> getSumnjiviKupci() {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		ArrayList<Korisnik> sumnjiviKupci = new ArrayList<Korisnik>();
		for (Korisnik kupac : korisnici) {
			if (kupac.getUloga().equals(Uloga.KUPAC)) {
				ArrayList<Karta> otkazaneKarte = new ArrayList<Karta>();
				for (Karta karta : kupac.getSveKarte()) {
					if (karta.getStatusKarte().equals(StatusKarte.ODUSTANAK)) {
						otkazaneKarte.add(karta);
					}
				}
				if (otkazaneKarte.size() > 5) {
					Collections.sort(otkazaneKarte, new Comparator<Karta>() {
						public int compare(Karta k1, Karta k2) {
							return k1.getDatumOtkazivanjaKarte().compareTo(k2.getDatumOtkazivanjaKarte());
						}
					});
					for (int i = 0; i < otkazaneKarte.size(); i++) {
						if (otkazaneKarte.size() <= i + 5) {
							break;
						}
						Duration diff = Duration.between(otkazaneKarte.get(i).getDatumOtkazivanjaKarte(), 
								otkazaneKarte.get(i + 5).getDatumOtkazivanjaKarte());
						if (diff.toDays() <= 30) {
							sumnjiviKupci.add(kupac);
							break;
						}
					}
				}
			}
		}
		return sumnjiviKupci;
	}
	
	public ArrayList<Manifestacija> obrisiManifestaciju(String nazivManifestacije) throws IOException {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		for (Manifestacija m : manifestacije) {
			if (m.getNaziv().equals(nazivManifestacije)) {
				m.setObrisana(true);
				break;
			}
		}
		PomocneFunkcije.upisi(manifestacije, Konstante.FAJL_MANIFESTACIJE);
		azurirajManifesacijuProdavca(nazivManifestacije);
		return manifestacije;
	}
	
	private void azurirajManifesacijuProdavca(String nazivManifestacije) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		for (Korisnik prodavac : korisnici) {
			for (Manifestacija m : prodavac.getManifestacije()) {
				if (m.getNaziv().equals(nazivManifestacije)) {
					m.setObrisana(true);
					PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
					return;
				}
			}
		}
	}
	
	public ArrayList<Karta> obrisiKartu(String idKarte) throws IOException {
		ArrayList<Karta> karte = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KARTE),
                new TypeReference<ArrayList<Karta>>(){});
		for (Karta k : karte) {
			if (k.getIdentifikatorKarte().equals(idKarte)) {
				k.setObrisana(true);
				break;
			}
		}
		PomocneFunkcije.upisi(karte, Konstante.FAJL_KARTE);
		azurirajStatusKartaKupca(idKarte);
		return karte;
	}
	
	private void azurirajStatusKartaKupca(String idKarte) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		for (Korisnik kupac : korisnici) {
			for (Karta karta : kupac.getSveKarte()) {
				if (karta.getIdentifikatorKarte().equals(idKarte)) {
					karta.setObrisana(true);
					PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
					return;
				}
			}
		}
	}
}
