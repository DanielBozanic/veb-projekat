package DAO;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Komentar;
import utils.Konstante;
import utils.PomocneFunkcije;

public class KomentarDAO {
	
	public KomentarDAO() {}
	
	public void objaviKomentar(Komentar komentar) throws IOException {
		ArrayList<Komentar> komentari = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KOMENTARI), new TypeReference<ArrayList<Komentar>>(){});
		komentar.setIdKomentara(generisiIDKometara());
		komentari.add(komentar);
		PomocneFunkcije.upisi(komentari, Konstante.FAJL_KOMENTARI);
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
	
	public boolean odobriKomentar(String idKomentara) throws IOException {
		ArrayList<Komentar> komentari = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KOMENTARI), new TypeReference<ArrayList<Komentar>>(){});
		boolean valid = false;
		for (Komentar k : komentari) {
			if (k.getIdKomentara().equals(idKomentara)) {
				k.setOdobren(true);
				valid = true;
				break;
			}
		}
		if (valid) {
			PomocneFunkcije.upisi(komentari, Konstante.FAJL_KOMENTARI);
		}
		
		return valid;
	}
	
	public ArrayList<Komentar> getOdobreniKomentariZaManifestaciju(String nazivManifestacije) {
		ArrayList<Komentar> komentari = PomocneFunkcije
				.ucitaj(new File(Konstante.FAJL_KOMENTARI), new TypeReference<ArrayList<Komentar>>(){});
		ArrayList<Komentar> odobreniKomentariZaManifestaciju = new ArrayList<Komentar>();
		
		for (Komentar k : komentari) {
			if (k.getManifestacija().getNaziv().equals(nazivManifestacije) && k.isOdobren()) {
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
			if (k.getManifestacija().getNaziv().equals(nazivManifestacije)) {
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
