package services;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


import DAO.ProdavacDAO;
import beans.Korisnik;
import utils.PomocneFunkcije;

@Path("/prodavci")
public class ProdavacServis {
	@Context
	ServletContext ctx;
	
	public ProdavacServis() {
		
	}
	
	@PostConstruct
	public void init() throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		if(ctx.getAttribute("prodavacDAO") == null) {
			PomocneFunkcije.kreirajBaseFolder();
			ctx.setAttribute("prodavacDAO", new ProdavacDAO());
		}
	}

	@POST
	@Path("/dodajProdavca")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean dodajProdavca(Korisnik korisnik, @Context HttpServletRequest request) throws IOException {
		Korisnik.InitProdavac(korisnik);
		ProdavacDAO dao = (ProdavacDAO) ctx.getAttribute("prodavacDAO");
		return dao.dodajProdavca(korisnik);
	}
}
