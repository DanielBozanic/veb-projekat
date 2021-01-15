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

import DAO.KupacDAO;
import beans.Karta;
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
	
	@POST
	@Path("/rezervacijaKarte")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean rezervacijaKarte(Karta karta, @Context HttpServletRequest request) 
			throws IOException, InterruptedException {
		KupacDAO dao = (KupacDAO) ctx.getAttribute("kupacDAO");
		Korisnik trenutniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		return dao.rezervacijaKarte(karta, trenutniKorisnik.getKorisnickoIme());
	}
	
	@POST
	@Path("/odustanakRezervacije")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> odustanakRezervacije(String idKarte, @Context HttpServletRequest request) 
			throws IOException, InterruptedException {
		KupacDAO dao = (KupacDAO) ctx.getAttribute("kupacDAO");
		Korisnik trenutniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		return dao.odustanakRezervacije(idKarte, trenutniKorisnik.getKorisnickoIme());
	}
	
	@GET
	@Path("/getKarteValidneZaOdustanak")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> getKarteValidneZaOdustanak(@Context HttpServletRequest request) {
		Korisnik ulogovaniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		KupacDAO dao = (KupacDAO) ctx.getAttribute("kupacDAO");
		return dao.getKarteValidneZaOdustanak(ulogovaniKorisnik.getKorisnickoIme());
	}
}
