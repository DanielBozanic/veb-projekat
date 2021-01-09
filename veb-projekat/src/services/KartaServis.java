package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	
	@POST
	@Path("/rezervacijaKarte")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean rezervacijaKarte(Karta karta, @Context HttpServletRequest request) 
			throws IOException, InterruptedException {
		KartaDAO dao = (KartaDAO) ctx.getAttribute("kartaDAO");
		Korisnik trenutniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		return dao.rezervacijaKarte(karta, trenutniKorisnik.getKorisnickoIme());
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
}

