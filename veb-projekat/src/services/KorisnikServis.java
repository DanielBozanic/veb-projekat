package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import DAO.KorisnikDAO;
import beans.Korisnik;
import utils.PomocneFunkcije;

@Path("/korisnici")
public class KorisnikServis {
	
	@Context
	ServletContext ctx;
	
	public KorisnikServis() {}
	
	@PostConstruct 
	public void init() {
		if (ctx.getAttribute("korisnikDAO") == null) {
			PomocneFunkcije.kreirajBaseFolder();
			ctx.setAttribute("korisnikDAO", new KorisnikDAO());
		}
	}
	
	@GET
	@Path("/trenutniKorisnik")
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik getPodaciTrenutniKorisnik(@Context HttpServletRequest request) {
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		KorisnikDAO korisnikDAO = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if (korisnik != null) {
			return korisnikDAO.getPodaciTrenutniKorisnik(korisnik.getKorisnickoIme());
		} else {
			return null;
		}
			
	}
	
	@POST
	@Path("/izmenaPodatakaTrenutnogKorisnika")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik getPodaciTrenutniKorisnik(Korisnik korisnik) throws IOException {
		KorisnikDAO korisnikDAO = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return korisnikDAO.izmenaPodatakaTrenutnogKorisnika(korisnik);
	}
	
	@GET
	@Path("/getKorisnici")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> getKorisnici() {
		KorisnikDAO korisnikDAO = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return korisnikDAO.getKorisnici();
	}
	
	@GET
	@Path("/getSortiraneKorisnike")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> getSortiraneKorisnike(@QueryParam("kriterijumSortiranja") String kriterijumSortiranja, 
			@QueryParam("kriterijumSortiranja2") String kriterijumSortiranja2) {
		KorisnikDAO korisnikDAO = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return korisnikDAO.getSortiraneKorisnike(kriterijumSortiranja, kriterijumSortiranja2);
	}
	
	@GET
	@Path("/linkovi")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SimpleEntry<String, String>> getLinkovi(@Context HttpServletRequest request) {
		KorisnikDAO korisnikDAO = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik trenutniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		return korisnikDAO.getLinkovi(trenutniKorisnik);
	}
	
}
