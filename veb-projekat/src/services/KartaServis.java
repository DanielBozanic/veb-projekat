package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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

import DAO.KartaDAO;
import beans.Karta;
import beans.Korisnik;
import utils.PomocneFunkcije;

@Path("/karte")
public class KartaServis {
	@Context
	ServletContext ctx;
	
	public KartaServis() {
		
	}
	
	@PostConstruct
	public void init() throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		if(ctx.getAttribute("kartaDAO") == null) {
			PomocneFunkcije.kreirajBaseFolder();
			ctx.setAttribute("kartaDAO", new KartaDAO());
		}
	}
	
	@GET
	@Path("/getKarte")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> getKarte() {
		KartaDAO dao = (KartaDAO) ctx.getAttribute("kartaDAO");
		return dao.getKarte();
	}
	
	@GET
	@Path("/getRezervisaneKarte")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> getRezervisaneKarte() {
		KartaDAO dao = (KartaDAO) ctx.getAttribute("kartaDAO");
		return dao.getRezervisaneKarte();
	}
	
	@GET
	@Path("/getKarteKupca")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> getKarteKupca(@Context HttpServletRequest request) {
		Korisnik ulogovaniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		KartaDAO dao = (KartaDAO) ctx.getAttribute("kartaDAO");
		return dao.getKarteKupca(ulogovaniKorisnik.getKorisnickoIme());
	}
	
	@GET
	@Path("/getKupciKojiSuRezervisaliKarte")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Korisnik> getKupciKojiSuRezervisaliKarte(@Context HttpServletRequest request) {
		Korisnik ulogovaniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		KartaDAO dao = (KartaDAO) ctx.getAttribute("kartaDAO");
		return dao.getKupciKojiSuRezervisaliKarte(ulogovaniKorisnik.getKorisnickoIme());
	}
}

