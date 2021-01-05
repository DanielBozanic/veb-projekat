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
			if (m.getNaziv().equals(manifestacija.getNaziv())) {
				valid = false;
				break;
			}
		}
		
		if (valid) {
			manifestacije.add(manifestacija);
			PomocneFunkcije.upisi(manifestacije, Konstante.FAJL_MANIFESTACIJE);
			if (!azurirajPodatke(korisnickoIme, manifestacija))
				valid = false;
		}
		
		return valid;
	}
	
	private boolean azurirajPodatke(String korisnickoIme, Manifestacija manifestacija) throws IOException {
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
	
}
