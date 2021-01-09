package DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Korisnik;
import beans.Manifestacija;
import utils.Konstante;
import utils.PomocneFunkcije;

public class ManifestacijaDAO {
	
	
	public ManifestacijaDAO()  {
	}
	
	public boolean dodajManifestaciju(Manifestacija manifestacija, String korisnickoIme) throws IOException {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		boolean valid = true;
		for (Manifestacija m : manifestacije) {
			if (m.getNaziv().equals(manifestacija.getNaziv()) || 
					(m.getDatumIVremeOdrzavanja().equals(manifestacija.getDatumIVremeOdrzavanja()) &&
					m.getLokacija().getUlicaIBroj().equals(manifestacija.getLokacija().getUlicaIBroj()))) {
				valid = false;
				break;
			}
		}
		
		if (valid) {
			manifestacije.add(manifestacija);
			PomocneFunkcije.upisi(manifestacije, Konstante.FAJL_MANIFESTACIJE);
			if (!dodajManifestacijuProdavcu(korisnickoIme, manifestacija))
				valid = false;
		}
		
		return valid;
	}
	
	private boolean dodajManifestacijuProdavcu(String korisnickoIme, Manifestacija manifestacija) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		boolean valid = false;
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				k.getManifestacije().add(manifestacija);
				valid = true;
				break;
			}
		}
		
		if (valid) {
			PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
		}
		
		return valid;
	}
	
	public ArrayList<Manifestacija> getManifestacije() {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		return manifestacije;
	}
	
	public ArrayList<Manifestacija> getAktivneManifestacije() {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		ArrayList<Manifestacija> aktivneManifestacije = new ArrayList<Manifestacija>();
		for (Manifestacija m : manifestacije) {
			if (m.isAktivan()) {
				aktivneManifestacije.add(m);
			}
		}
		return aktivneManifestacije;
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
	
}
