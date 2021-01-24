package DAO;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Komentar;
import beans.Manifestacija;
import utils.Konstante;
import utils.PomocneFunkcije;

public class KomentarDAO {
	
	public KomentarDAO() {}
	
	public boolean objaviKomentar(Komentar komentar) throws IOException {
		ArrayList<Komentar> komentari = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KOMENTARI), new TypeReference<ArrayList<Komentar>>(){});
		boolean valid = true;
		for (Komentar k : komentari) {
			if (k.getKupacKarte().equals(komentar.getKupacKarte())) {
				valid = false;
				break;
			}
		}
		if (valid) {
			komentar.setIdKomentara(generisiIDKometara());
			komentari.add(komentar);
			PomocneFunkcije.upisi(komentari, Konstante.FAJL_KOMENTARI);
		}
		return valid;
	}
	
	private String generisiIDKometara() {
		String id = "";
		LocalDateTime now = LocalDateTime.now();
		int value = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("HHmmssSSS")));
		DecimalFormat decimalFormat = new DecimalFormat("0000000000");
		now.format(DateTimeFormatter.ofPattern("ddMMHHmmss"));
		id = decimalFormat.format(value);
		return id;
	}
	
	public ArrayList<Komentar> odobriKomentar(String idKomentara) throws IOException {
		ArrayList<Komentar> komentari = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KOMENTARI), new TypeReference<ArrayList<Komentar>>(){});
		boolean valid = false;
		Komentar komentar = null;
		for (Komentar k : komentari) {
			if (k.getIdKomentara().equals(idKomentara)) {
				k.setOdobren(true);
				komentar = k;
				valid = true;
				break;
			}
		}
		if (valid) {
			PomocneFunkcije.upisi(komentari, Konstante.FAJL_KOMENTARI);
			racunajProsecnuOcenuManifestacije(komentar, komentari);
		}
		return komentari;
	}
	
	private void racunajProsecnuOcenuManifestacije(Komentar komentar, ArrayList<Komentar> komentari) throws IOException {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE), new TypeReference<ArrayList<Manifestacija>>(){});
		double brojOcena = 0;
		double zbirSvihOcena = 0;
		for (Komentar k : komentari) {
			if (k.getManifestacija().getNaziv().equals(komentar.getManifestacija().getNaziv()) && k.isOdobren() && 
					!k.isObrisan()) {
				zbirSvihOcena += k.getOcena();
				brojOcena++;
			}
		}
		if (brojOcena != 0) {
			double prosecnaOcena = zbirSvihOcena / brojOcena;
			for (Manifestacija m : manifestacije) {
				if (m.getNaziv().equals(komentar.getManifestacija().getNaziv())) {
					m.setProsecnaOcena(prosecnaOcena);
					break;
				}
			}
		}
		PomocneFunkcije.upisi(manifestacije, Konstante.FAJL_MANIFESTACIJE);
	}
	
	public ArrayList<Komentar> obrisiKomentar(String idKomentara) throws IOException {
		ArrayList<Komentar> komentari = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KOMENTARI), new TypeReference<ArrayList<Komentar>>(){});
		boolean valid = false;
		Komentar komentar = null;
		for (Komentar k : komentari) {
			if (k.getIdKomentara().equals(idKomentara)) {
				k.setObrisan(true);
				komentar = k;
				valid = true;
				break;
			}
		}
		if (valid) {
			PomocneFunkcije.upisi(komentari, Konstante.FAJL_KOMENTARI);
			racunajProsecnuOcenuManifestacije(komentar, komentari);
		}
		return komentari;
	}
	
	public ArrayList<Komentar> getOdobreniKomentariZaManifestaciju(String nazivManifestacije) {
		ArrayList<Komentar> komentari = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KOMENTARI), new TypeReference<ArrayList<Komentar>>(){});
		ArrayList<Komentar> odobreniKomentariZaManifestaciju = new ArrayList<Komentar>();
		
		for (Komentar k : komentari) {
			if (k.getManifestacija().getNaziv().equals(nazivManifestacije) && k.isOdobren() && !k.isObrisan()) {
				odobreniKomentariZaManifestaciju.add(k);
			}
		}
		return odobreniKomentariZaManifestaciju;
	}
	
	public ArrayList<Komentar> getKomentariZaManifestaciju(String nazivManifestacije) {
		ArrayList<Komentar> komentari = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KOMENTARI), new TypeReference<ArrayList<Komentar>>(){});
		ArrayList<Komentar> komentariZaManifestaciju = new ArrayList<Komentar>();
		
		for (Komentar k : komentari) {
			if (k.getManifestacija().getNaziv().equals(nazivManifestacije) && !k.isObrisan()) {
				komentariZaManifestaciju.add(k);
			}
		}
		return komentariZaManifestaciju;
	}
	
	public ArrayList<Komentar> getKomentari() {
		ArrayList<Komentar> komentari = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KOMENTARI), new TypeReference<ArrayList<Komentar>>(){});
		return komentari;
	}
}
