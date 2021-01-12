package DAO;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Korisnik;
import beans.Manifestacija;
import utils.Konstante;
import utils.PomocneFunkcije;

public class ManifestacijaDAO {
	
	
	public ManifestacijaDAO()  {
	}
	
	public ArrayList<Manifestacija> getManifestacije() {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		return manifestacije;
	}
	
	public ArrayList<Manifestacija> getManfestacijeZaProdavca(String korisnickoIme){
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		
		ArrayList<Manifestacija> manifestacijeProdavac = new ArrayList<Manifestacija>();
		
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				manifestacijeProdavac = k.getManifestacije();
				break;
			}
		}
		
		return manifestacijeProdavac;
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
}
