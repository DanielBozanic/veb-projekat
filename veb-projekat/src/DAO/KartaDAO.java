package DAO;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Karta;
import beans.Korisnik;
import beans.Manifestacija;
import utils.Konstante;
import utils.PomocneFunkcije;

public class KartaDAO {
	
	public KartaDAO() {}
	
	public boolean rezervacijaKarte(Karta karta, String korisnickoIme) 
			throws IOException, InterruptedException {
		ArrayList<Karta> karte = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KARTE),
                new TypeReference<ArrayList<Karta>>(){});
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		String id = generisiIDKarte();
		
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				karta.setKupac(k.getKorisnickoIme());
				karta.setIdentifikatorKarte(id);
				k.getSveKarte().add(karta);
				karte.add(karta);
				k.setBrojSakupljenihBodova(k.getBrojSakupljenihBodova() + 
						racunajDobijenihBrojBodova(karta));
				if (azurirajManifestaciju(karta)) {
					PomocneFunkcije.upisi(karte, Konstante.FAJL_KARTE);
					PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
					return true;
				}
			}
		}
		
		return false;
	}
	
	private double racunajDobijenihBrojBodova(Karta karta) {
		return (karta.getCena()
				.multiply(new BigDecimal(karta.getBrojKarata()))
				.divide(new BigDecimal(1000))
				.multiply(new BigDecimal(133))).doubleValue();
	}
	
	private boolean azurirajManifestaciju(Karta karta) throws IOException {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		boolean valid = false;
		
		for (Manifestacija m : manifestacije) {
			if (karta.getManifestacija().getNaziv().equals(m.getNaziv())) {
				if (karta.getBrojKarata() < m.getBrojMesta()) {
					m.setBrojMesta(m.getBrojMesta() - karta.getBrojKarata());
					valid = true;
					break;
				}
			}
		}
		if (valid) {
			PomocneFunkcije.upisi(manifestacije, Konstante.FAJL_MANIFESTACIJE);
		}
		return valid;
	}
	
	private String generisiIDKarte() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMHHmmss"));	
	}
}
