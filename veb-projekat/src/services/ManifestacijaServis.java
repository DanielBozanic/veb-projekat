package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import DAO.ManifestacijaDAO;
import beans.Korisnik;
import beans.Manifestacija;
import utils.PomocneFunkcije;

@Path("/manifestacije")
public class ManifestacijaServis {
	@Context
	ServletContext ctx;
	
	public ManifestacijaServis() {
		
	}
	
	@PostConstruct
	public void init() throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		if(ctx.getAttribute("manifestacijaDAO") == null) {
			PomocneFunkcije.kreirajBaseFolder();
			ctx.setAttribute("manifestacijaDAO", new ManifestacijaDAO());
		}
	}
	
	@GET
	@Path("/getManfestacijeZaProdavca")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> getManfestacijeZaProdavca(@Context HttpServletRequest request) {
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		Korisnik ulogovaniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		return dao.getManfestacijeZaProdavca(ulogovaniKorisnik.getKorisnickoIme());
	}
	
	@GET
	@Path("/getManifestacije")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> getManifestacije() {
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		return dao.getManifestacije();
	}
	
	@GET
	@Path("/getAktivneManifestacije")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> getAktivneManifestacije() {
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		return dao.getAktivneManifestacije();
	}
}
