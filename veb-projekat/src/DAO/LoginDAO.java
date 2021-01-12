package DAO;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.util.ArrayList;

import beans.Korisnik;
import utils.Konstante;
import utils.PomocneFunkcije;

public class LoginDAO {
	
	public LoginDAO() {
		
	}
	
	public Korisnik pronadjiKorisnika(String korisnickoIme, String lozinka) {
		ArrayList<Korisnik> korisnici = PomocneFunkcije.ucitaj(new File(Konstante.FAJL_KORISNICI),
                new TypeReference<ArrayList<Korisnik>>(){});
		Korisnik korisnik = null;
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme) && k.getLozinka().equals(lozinka)) {
				korisnik = k;
				break;
			}
		}
		return korisnik;
	}
}
