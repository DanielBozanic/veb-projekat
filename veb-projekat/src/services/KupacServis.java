package services;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import DAO.KupacDAO;
import beans.Korisnik;
import utils.PomocneFunkcije;

@Path("/kupci")
public class KupacServis {
	@Context
	ServletContext ctx;
	
	public KupacServis() {
		
	}
	
	@PostConstruct
	public void init() throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		if(ctx.getAttribute("kupacDAO") == null) {
			PomocneFunkcije.kreirajBaseFolder();
			ctx.setAttribute("kupacDAO", new KupacDAO());
		}
	}
	
	@POST
	@Path("/registrujKupca")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean registrujKupca(Korisnik korisnik) throws IOException {
		Korisnik.InitKupac(korisnik);
		KupacDAO dao = (KupacDAO) ctx.getAttribute("kupacDAO");
		return dao.registrujKupca(korisnik);
	}
}
