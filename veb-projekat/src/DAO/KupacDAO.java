package DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Korisnik;

public class KupacDAO {
	
	private ArrayList<Korisnik> kupci = new ArrayList<Korisnik>();
	
	public KupacDAO() {}
	
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
			upisiUJSONFajl();
		}
		return valid;
	}
	
	private void upisiUJSONFajl() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File(System.getProperty("user.home") + "/podaciWeb/kupci.json"), kupci);
	}
}
