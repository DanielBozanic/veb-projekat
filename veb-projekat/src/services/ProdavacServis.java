package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import DAO.ProdavacDAO;
import beans.Korisnik;
import beans.Manifestacija;
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
	@Path("/dodajManifestaciju")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean dodajManifestaciju(Manifestacija manifestacija, @Context HttpServletRequest request) throws IOException {
		Manifestacija.InitManifestacija(manifestacija);
		Korisnik ulogovaniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		ProdavacDAO dao = (ProdavacDAO) ctx.getAttribute("prodavacDAO");
		return dao.dodajManifestaciju(manifestacija, ulogovaniKorisnik.getKorisnickoIme());		
	}
	
	@POST
	@Path("/izmeniManifestaciju")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> izmeniManifestaciju(Manifestacija manifestacija, @Context HttpServletRequest request) throws IOException {
		Korisnik ulogovaniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		ProdavacDAO dao = (ProdavacDAO) ctx.getAttribute("prodavacDAO");
		return dao.izmeniManifestaciju(manifestacija, ulogovaniKorisnik.getKorisnickoIme());		
	}
}
