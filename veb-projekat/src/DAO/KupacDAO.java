package DAO;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.ImeTipa;
import beans.Karta;
import beans.Korisnik;
import beans.Manifestacija;
import utils.Konstante;
import utils.PomocneFunkcije;

public class KupacDAO {
	
	public KupacDAO()  {
		
	}
	
	public boolean registrujKupca(Korisnik kupac) throws IOException {
		ArrayList<Korisnik> korisnici = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KORISNICI), new TypeReference<ArrayList<Korisnik>>(){});
		boolean valid = true;
		for (Korisnik k : korisnici) {
    		if (k.getKorisnickoIme().equals(kupac.getKorisnickoIme())) {
    			valid = false;
    			break;
    		}
    	}
		
		if (valid) {
			korisnici.add(kupac);
			PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
		}
		return valid;
	}
	
	public boolean rezervacijaKarte(Karta karta, String korisnickoIme) 
			throws IOException, InterruptedException {
		ArrayList<Karta> karte = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KARTE),
                new TypeReference<ArrayList<Karta>>(){});
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		ArrayList<String> ids = generisiIDKarte(karta.getBrojKarata());
		
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				if (!azurirajManifestaciju(karta)) {
					break;
				}
				ArrayList<Karta> korisnikoveKarte = k.getSveKarte();
				for (String id : ids) {
					Karta novaKarta = new Karta();
					novaKarta.setIdentifikatorKarte(id);
					novaKarta.setCena(karta.getCena());
					novaKarta.setDatumIVremeManifestacije(karta.getDatumIVremeManifestacije());
					novaKarta.setManifestacija(karta.getManifestacija());
					novaKarta.setKupac(k.getKorisnickoIme());
					novaKarta.setStatusKarte(karta.getStatusKarte());
					novaKarta.setTipKarte(karta.getTipKarte());
					korisnikoveKarte.add(novaKarta);
					karte.add(novaKarta);
				}
				azurirajKupca(k, karta);
				PomocneFunkcije.upisi(karte, Konstante.FAJL_KARTE);
				PomocneFunkcije.upisi(korisnici, Konstante.FAJL_KORISNICI);
				return true;
			}
		}
		
		return false;
	}
	
	private void azurirajKupca(Korisnik kupac, Karta karta) {
		 BigDecimal cena = karta.getCena();
		 if (kupac.getTipKupca().getImeTipa().equals(ImeTipa.SREBRNI)) {
			 cena = cena.multiply(new BigDecimal((100.0 - Konstante.SREBRNI_POPUST) / 100.0));
		 } else if (kupac.getTipKupca().getImeTipa().equals(ImeTipa.ZLATNI)) {
			 cena = cena.multiply(new BigDecimal((100.0 - Konstante.ZLATNI_POPUST) / 100.0));
		 }
		 
		 double bodovi = kupac.getBrojSakupljenihBodova() + (cena
				.multiply(new BigDecimal(karta.getBrojKarata()))
				.divide(new BigDecimal(1000))
				.multiply(new BigDecimal(133))).doubleValue();
		 
		 if (bodovi >= Konstante.SREBRNI_TRAZENI_PRAG) {
			 kupac.getTipKupca().setImeTipa(ImeTipa.SREBRNI);
			 kupac.getTipKupca().setPopust(Konstante.SREBRNI_POPUST);
			 kupac.getTipKupca().setTrazeniBrojBodova(Konstante.ZLATNI_TRAZENI_PRAG);
		 }
		 if (bodovi >= Konstante.ZLATNI_TRAZENI_PRAG) {
			 kupac.getTipKupca().setImeTipa(ImeTipa.ZLATNI);
			 kupac.getTipKupca().setPopust(Konstante.ZLATNI_POPUST);
		 }
		 
		 kupac.setBrojSakupljenihBodova(bodovi);
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
	
	private ArrayList<String> generisiIDKarte(int brojKarata) {
		ArrayList<String> ids = new ArrayList<String>();
		LocalDateTime now = LocalDateTime.now();
		int value = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("HHmmssSSS")));
		DecimalFormat decimalFormat = new DecimalFormat("0000000000");
		for (int i = 0; i < brojKarata; i++) {
			now.format(DateTimeFormatter.ofPattern("ddMMHHmmss"));
			ids.add(decimalFormat.format(value));
			value += 1;
		}
		return ids;
	}
}
