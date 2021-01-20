package DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Karta;
import beans.Korisnik;
import beans.Manifestacija;
import beans.StatusKarte;
import utils.Konstante;
import utils.PomocneFunkcije;

public class KartaDAO {
	
	public KartaDAO() {}
	
	public ArrayList<Karta> getKarte() {
		ArrayList<Karta> karte = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KARTE),
                new TypeReference<ArrayList<Karta>>(){});
		return karte;
	}
	
	public ArrayList<Karta> getRezervisaneKarte() {
		ArrayList<Karta> karte = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KARTE),
                new TypeReference<ArrayList<Karta>>(){});
		ArrayList<Karta> rezervisaneKarte = new ArrayList<Karta>();
		
		for (Karta k : karte) {
			if (k.getStatusKarte().equals(StatusKarte.REZERVISANA)) {
				rezervisaneKarte.add(k);
			}
		}
		
		return rezervisaneKarte;
	}
	
	public ArrayList<Karta> getKarteKupca(String korisnickoIme) {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		ArrayList<Karta> karteKupca = new ArrayList<Karta>();
		
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				karteKupca = k.getSveKarte();
				break;
			}
		}
		
		return karteKupca;
	}
	
	public Collection<Korisnik> getKupciKojiSuRezervisaliKarte(String prodavacKorisnickoIme) {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		ArrayList<Manifestacija> manifestacijeProdavac = new ArrayList<Manifestacija>();
		ArrayList<Karta> kupciKarte = new ArrayList<Karta>();
		HashMap<String, Korisnik> kupci = new HashMap<String, Korisnik>();
		
		for (Korisnik prodavac : korisnici) {
			if (prodavac.getKorisnickoIme().equals(prodavacKorisnickoIme)) {
				manifestacijeProdavac = prodavac.getManifestacije();
				break;
			}
		}
		
		for (Korisnik kupac : korisnici) {
			kupciKarte.addAll(kupac.getSveKarte());
		}
		
		for (Manifestacija m : manifestacijeProdavac) {
			for (Karta karta : kupciKarte) {
				if (m.getNaziv().equals(karta.getManifestacija().getNaziv())) {
					Korisnik kupac = getKupacPoKorisnickomImenu(karta.getKupac());
					if (!kupci.containsKey(kupac.getKorisnickoIme())) {
						kupci.put(kupac.getKorisnickoIme(), kupac);
					}
				}
			}
		}
		
		return kupci.values();
	}
	
	private Korisnik getKupacPoKorisnickomImenu(String korisnickoIme) {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		Korisnik kupac = null;
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				kupac = k;
				break;
			}
		}
		
		return kupac;
	}
	
	public ArrayList<Karta> getSortiraneKarte(String kriterijumSortiranja, String kriterijumSortiranja2, 
			String korisnickoIme) {
		ArrayList<Karta> karteKupca = getKarteKupca(korisnickoIme);
		if (kriterijumSortiranja2.equals("opadajuce")) {
			sortOpadajuce(karteKupca, kriterijumSortiranja);
		} else if (kriterijumSortiranja2.equals("rastuce")) {
			sortRastuce(karteKupca, kriterijumSortiranja);
		}
		return karteKupca;
	}
	
	private static void sortRastuce(ArrayList<Karta> karteKupca, String kriterijumSortiranja) {
		if (kriterijumSortiranja.equals("manifestacija")) {
			Collections.sort(karteKupca, new Comparator<Karta>() {
				public int compare(Karta k1, Karta k2) {
					return k1.getManifestacija().getNaziv().compareTo(k2.getManifestacija().getNaziv());
				}
			});
		} else if (kriterijumSortiranja.equals("datumIVremeOdrzavanja")) {
			Collections.sort(karteKupca, new Comparator<Karta>() {
				public int compare(Karta k1, Karta k2) {
					return k1.getDatumIVremeManifestacije().compareTo(k2.getDatumIVremeManifestacije());
				}
			});
		} else if (kriterijumSortiranja.equals("cenaKarte")) {
			Collections.sort(karteKupca, new Comparator<Karta>() {
				public int compare(Karta k1, Karta k2) {
					double diff = k1.getCena().doubleValue() - k2.getCena().doubleValue();
					return diff > 0 ? 1 : (diff == 0 ? 0 : -1);
				}
			});
		}
	}
	
	private void sortOpadajuce(ArrayList<Karta> karteKupca, String kriterijumSortiranja) {
		if (kriterijumSortiranja.equals("manifestacija")) {
			Collections.sort(karteKupca, new Comparator<Karta>() {
				public int compare(Karta k1, Karta k2) {
					return k2.getManifestacija().getNaziv().compareTo(k1.getManifestacija().getNaziv());
				}
			});
		} else if (kriterijumSortiranja.equals("datumIVremeOdrzavanja")) {
			Collections.sort(karteKupca, new Comparator<Karta>() {
				public int compare(Karta k1, Karta k2) {
					return k2.getDatumIVremeManifestacije().compareTo(k1.getDatumIVremeManifestacije());
				}
			});
		} else if (kriterijumSortiranja.equals("cenaKarte")) {
			Collections.sort(karteKupca, new Comparator<Karta>() {
				public int compare(Karta k1, Karta k2) {
					double diff = k2.getCena().doubleValue() - k1.getCena().doubleValue();
					return diff > 0 ? 1 : (diff == 0 ? 0 : -1);
				}
			});
		}
	}
}
