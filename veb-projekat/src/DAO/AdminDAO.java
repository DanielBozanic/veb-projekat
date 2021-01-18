package DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Korisnik;
import beans.Manifestacija;
import utils.Konstante;
import utils.PomocneFunkcije;

public class AdminDAO {
	
	public AdminDAO() {}
	
	public boolean dodajProdavca(Korisnik prodavac) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KORISNICI), new TypeReference<ArrayList<Korisnik>>(){});
		boolean valid = true;
		for (Korisnik k : korisnici) {
    		if (k.getKorisnickoIme().equals(prodavac.getKorisnickoIme())) {
    			valid = false;
    			break;
    		}
    	}
		
		if (valid) {
			korisnici.add(prodavac);
			PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
		}
		return valid;
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

}
