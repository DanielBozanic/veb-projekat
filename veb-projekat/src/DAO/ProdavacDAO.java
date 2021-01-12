package DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Korisnik;
import beans.Manifestacija;
import utils.Konstante;
import utils.PomocneFunkcije;

public class ProdavacDAO {
	
	
	public ProdavacDAO()  {
		
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
	
	public boolean izmeniManifestaciju(Manifestacija manifestacija, String korisnickoIme) throws IOException {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		boolean valid = true;
		for (Manifestacija m : manifestacije) {
			if (m.getDatumIVremeOdrzavanja().equals(manifestacija.getDatumIVremeOdrzavanja()) &&
				m.getLokacija().getUlicaIBroj().equals(manifestacija.getLokacija().getUlicaIBroj())) {
				valid = false;
				break;
			}
		}
		
		if (valid) {
			for (Manifestacija m : manifestacije) {
				if (m.getNaziv().equals(manifestacija.getNaziv())) {
					m.setTipManifestacije(manifestacija.getTipManifestacije());
					m.setBrojMesta(manifestacija.getBrojMesta());
					m.setCenaRegularKarte(manifestacija.getCenaRegularKarte());
					m.setLokacija(manifestacija.getLokacija());
					m.setDatumIVremeOdrzavanja(manifestacija.getDatumIVremeOdrzavanja());
					m.setPosterManifestacije(manifestacija.getPosterManifestacije());
					break;
				}
			}
			PomocneFunkcije.upisi(manifestacije, Konstante.FAJL_MANIFESTACIJE);
			if (!azurirajProdavca(korisnickoIme, manifestacija)) {
				valid = false;
			}
		}
		
		return valid;
	}
	
	private boolean azurirajProdavca(String korisnickoIme, Manifestacija manifestacija) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		boolean valid = false;
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				for (Manifestacija m : k.getManifestacije()) {
					if (m.getNaziv().equals(manifestacija.getNaziv())) {
						m.setTipManifestacije(manifestacija.getTipManifestacije());
						m.setBrojMesta(manifestacija.getBrojMesta());
						m.setCenaRegularKarte(manifestacija.getCenaRegularKarte());
						m.setLokacija(manifestacija.getLokacija());
						m.setDatumIVremeOdrzavanja(manifestacija.getDatumIVremeOdrzavanja());
						m.setPosterManifestacije(manifestacija.getPosterManifestacije());
						valid = true;
						break;
					}
				}
			}
		}
		
		if (valid) {
			PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
		}
		
		return valid;
	}
}
