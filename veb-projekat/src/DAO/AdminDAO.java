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
	
	public boolean promeniStatusManifestacije(String naziv) throws IOException {
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
		
		return valid;
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
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				k.setBlokiran(true);
				PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
				return korisnici;
			}
		}
		
		return null;
	}
	
	public ArrayList<Korisnik> odblokirajKorisnika(String korisnickoIme) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				k.setBlokiran(false);
				PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
				return korisnici;
			}
		}
		
		return null;
	}
	
	public ArrayList<Korisnik> blokirajSumnjivogKupca(String korisnickoIme) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		ArrayList<Korisnik> sumljiviKupci = getSumnjiviKupci();
		
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				k.setBlokiran(true);
				for (Korisnik kupac : sumljiviKupci) {
					if (kupac.getKorisnickoIme().equals(korisnickoIme)) {
						kupac.setBlokiran(true);
						PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
						return sumljiviKupci;
					}
				}
			}
		}
		
		return null;
	}
	
	public ArrayList<Korisnik> odblokirajSumnjivogKupca(String korisnickoIme) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		ArrayList<Korisnik> sumljiviKupci = getSumnjiviKupci();
		
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				k.setBlokiran(false);
				for (Korisnik kupac : sumljiviKupci) {
					if (kupac.getKorisnickoIme().equals(korisnickoIme)) {
						kupac.setBlokiran(false);
						PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
						return sumljiviKupci;
					}
				}
			}
		}
		
		return null;
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
}
