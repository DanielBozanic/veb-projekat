package DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;

import beans.Korisnik;
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
}
