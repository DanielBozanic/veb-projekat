package DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Korisnik;
import utils.Konstante;
import utils.PomocneFunkcije;

public class KupacDAO {
	
	private ArrayList<Korisnik> kupci = new ArrayList<Korisnik>();
	
	public KupacDAO()  {
		kupci = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KUPCI),
                new TypeReference<ArrayList<Korisnik>>() {});
		PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>() {});
		if (kupci == null) {
			kupci = new ArrayList<Korisnik>();
		}
	}
	
	public boolean registrujKupca(Korisnik kupac) throws IOException {
		boolean valid = true;
		for (Korisnik k : kupci) {
    		if (k.getKorisnickoIme().equals(kupac.getKorisnickoIme())) {
    			valid = false;
    			break;
    		}
    	}
		
		if (valid) {
			kupci.add(kupac);
			PomocneFunkcije.upisi(kupci, Konstante.FAJL_KUPCI);
			PomocneFunkcije.upisi(kupci, Konstante.FAJL_KORISNICI);
		}
		return valid;
	}
	
	public Korisnik izmeniKupca(Korisnik kupac) throws IOException {
		 for (Korisnik k : kupci) {
			 if (k.getKorisnickoIme().equals(kupac.getKorisnickoIme())) {
				 kupci.remove(k);
				 kupci.add(kupac);
				 PomocneFunkcije.upisi(kupci, Konstante.FAJL_KUPCI);
				 PomocneFunkcije.upisi(kupci, Konstante.FAJL_KORISNICI);
				 return kupac;
			 }
		 }
		 return null;
	}
}
