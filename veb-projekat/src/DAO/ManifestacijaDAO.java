package DAO;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	
	public ArrayList<Manifestacija> getManifestacijeZaProdavca(String korisnickoIme){
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		ArrayList<Manifestacija> manifestacijeProdavac = new ArrayList<Manifestacija>();
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme) && !k.isObrisan()) {
				for (Manifestacija m : k.getManifestacije()) {
					if (!m.isObrisana()) {
						manifestacijeProdavac.add(m);
					}
				}
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
			if (m.isAktivan() && !m.isObrisana()) {
				aktivneManifestacije.add(m);
			}
		}
		sortRastuce(aktivneManifestacije, "datumIVremeOdrzavanja");
		return aktivneManifestacije;
	}
	
	public ArrayList<Manifestacija> getAktuelneManifestacije() {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		ArrayList<Manifestacija> aktuelneManifestacije = new ArrayList<Manifestacija>();
		for (Manifestacija m : manifestacije) {
			LocalDateTime trenutniDatumIVreme = LocalDateTime.now();
			if (m.isAktivan() && trenutniDatumIVreme.isBefore(m.getDatumIVremeOdrzavanja()) && !m.isObrisana()) {
				aktuelneManifestacije.add(m);
			}
		}
		return aktuelneManifestacije;
	}
	
	public Manifestacija getOdabranaManifestacija(String nazivManifestacije) {
		ArrayList<Manifestacija> manifestacije = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_MANIFESTACIJE),
                new TypeReference<ArrayList<Manifestacija>>(){});
		for (Manifestacija m : manifestacije) {
			if (m.getNaziv().equals(nazivManifestacije) && !m.isObrisana()) {
				return m;
			}
		}
		return null;
	}
	
	public ArrayList<Manifestacija> getSortiraneManifestacije(String kriterijumSortiranja, String kriterijumSortiranja2) {
		ArrayList<Manifestacija> manifestacije = getAktivneManifestacije();
		if (kriterijumSortiranja2.equals("opadajuce")) {
			sortOpadajuce(manifestacije, kriterijumSortiranja);
		} else if (kriterijumSortiranja2.equals("rastuce")) {
			sortRastuce(manifestacije, kriterijumSortiranja);
		}
		return manifestacije;
	}
	
	private static void sortRastuce(ArrayList<Manifestacija> manifestacije, String kriterijumSortiranja) {
		if (kriterijumSortiranja.equals("manifestacija")) {
			Collections.sort(manifestacije, new Comparator<Manifestacija>() {
				public int compare(Manifestacija m1, Manifestacija m2) {
					return m1.getNaziv().compareTo(m2.getNaziv());
				}
			});
		} else if (kriterijumSortiranja.equals("datumIVremeOdrzavanja")) {
			Collections.sort(manifestacije, new Comparator<Manifestacija>() {
				public int compare(Manifestacija m1, Manifestacija m2) {
					return m1.getDatumIVremeOdrzavanja().compareTo(m2.getDatumIVremeOdrzavanja());
				}
			});
		} else if (kriterijumSortiranja.equals("lokacija")) {
			Collections.sort(manifestacije, new Comparator<Manifestacija>() {
				public int compare(Manifestacija m1, Manifestacija m2) {
					return m1.getLokacija().getMesto().compareTo(m2.getLokacija().getMesto());
				}
			});
		} else if (kriterijumSortiranja.equals("cenaKarte")) {
			Collections.sort(manifestacije, new Comparator<Manifestacija>() {
				public int compare(Manifestacija m1, Manifestacija m2) {
					double diff = m1.getCenaRegularKarte().doubleValue() - m2.getCenaRegularKarte().doubleValue();
					return diff > 0 ? 1 : (diff == 0 ? 0 : -1);
				}
			});
		}
	}
	
	private void sortOpadajuce(ArrayList<Manifestacija> manifestacije, String kriterijumSortiranja) {
		if (kriterijumSortiranja.equals("manifestacija")) {
			Collections.sort(manifestacije, new Comparator<Manifestacija>() {
				public int compare(Manifestacija m1, Manifestacija m2) {
					return m2.getNaziv().compareTo(m1.getNaziv());
				}
			});
		} else if (kriterijumSortiranja.equals("datumIVremeOdrzavanja")) {
			Collections.sort(manifestacije, new Comparator<Manifestacija>() {
				public int compare(Manifestacija m1, Manifestacija m2) {
					return m2.getDatumIVremeOdrzavanja().compareTo(m1.getDatumIVremeOdrzavanja());
				}
			});
		} else if (kriterijumSortiranja.equals("lokacija")) {
			Collections.sort(manifestacije, new Comparator<Manifestacija>() {
				public int compare(Manifestacija m1, Manifestacija m2) {
					return m2.getLokacija().getMesto().compareTo(m1.getLokacija().getMesto());
				}
			});
		} else if (kriterijumSortiranja.equals("cenaKarte")) {
			Collections.sort(manifestacije, new Comparator<Manifestacija>() {
				public int compare(Manifestacija m1, Manifestacija m2) {
					double diff = m2.getCenaRegularKarte().doubleValue() - m1.getCenaRegularKarte().doubleValue();
					return diff > 0 ? 1 : (diff == 0 ? 0 : -1);
				}
			});
		}
	}
}
