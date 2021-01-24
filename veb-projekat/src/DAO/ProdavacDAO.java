package DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Karta;
import beans.Komentar;
import beans.Korisnik;
import beans.Manifestacija;
import beans.Uloga;
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
				if (!m.isObrisana()) {
					valid = false;
					break;
				}
			}
		}
		if (valid) {
			manifestacije.add(manifestacija);
			PomocneFunkcije.upisi(manifestacije, Konstante.FAJL_MANIFESTACIJE);
			dodajManifestacijuProdavcu(korisnickoIme, manifestacija);
		}
		return valid;
	}
	
	private void dodajManifestacijuProdavcu(String korisnickoIme, Manifestacija manifestacija) throws IOException {
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
	}
	
	public ArrayList<Manifestacija> izmeniManifestaciju(Manifestacija manifestacija, String korisnickoIme) throws IOException {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		boolean valid = true;
		for (Manifestacija m : manifestacije) {
			if (!m.getNaziv().equals(manifestacija.getNaziv()) && 
				m.getDatumIVremeOdrzavanja().equals(manifestacija.getDatumIVremeOdrzavanja()) &&
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
			azurirajKarteKupaca(manifestacija);
			azurirajKarte(manifestacija);
			azurirajKomentare(manifestacija);
		}
		return azurirajProdavca(korisnickoIme, manifestacija);
	}
	
	private ArrayList<Manifestacija> azurirajProdavca(String korisnickoIme, Manifestacija manifestacija) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		ArrayList<Manifestacija> manifestacijeProdavac = new ArrayList<Manifestacija>();
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
						manifestacijeProdavac = k.getManifestacije();
						break;
					}
				}
			}
		}
		if (!manifestacijeProdavac.isEmpty()) {
			PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
		}
		return manifestacijeProdavac;
	}
	
	private void azurirajKarte(Manifestacija manifestacija) throws IOException {
		ArrayList<Karta> karte = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KARTE),
                new TypeReference<ArrayList<Karta>>(){});
		for (Karta karta : karte) {
			if (karta.getManifestacija().getNaziv().equals(manifestacija.getNaziv())) {
				karta.setManifestacija(manifestacija);
				karta.setDatumIVremeManifestacije(manifestacija.getDatumIVremeOdrzavanja());
				karta.setCena(manifestacija.getCenaRegularKarte());
			}
		}
		PomocneFunkcije.upisi(karte, Konstante.FAJL_KARTE);
	}
	
	private void azurirajKarteKupaca(Manifestacija manifestacija) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		for (Korisnik kupac : korisnici) {
			if (kupac.getUloga().equals(Uloga.KUPAC)) {
				for (Karta karta : kupac.getSveKarte()) {
					if (karta.getManifestacija().getNaziv().equals(manifestacija.getNaziv())) {
						karta.setManifestacija(manifestacija);
						karta.setDatumIVremeManifestacije(manifestacija.getDatumIVremeOdrzavanja());
						karta.setCena(manifestacija.getCenaRegularKarte());
					}
				}
			}
		}
		PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
	}
	
	private void azurirajKomentare(Manifestacija manifestacija) throws IOException {
		ArrayList<Komentar> komentari = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KOMENTARI),
                new TypeReference<ArrayList<Komentar>>(){});
		for (Komentar komentar : komentari) {
			if (komentar.getManifestacija().getNaziv().equals(manifestacija.getNaziv())) {
				komentar.setManifestacija(manifestacija);
			}
		}
		PomocneFunkcije.upisi(komentari, Konstante.FAJL_KOMENTARI);
	}
}
