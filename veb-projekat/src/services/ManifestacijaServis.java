package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	@Path("/getManifestacije")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> getManifestacije() {
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		return dao.getManifestacije();
	}
	
	@GET
	@Path("/getManifestacijeZaProdavca")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> getManifestacijeZaProdavca(@Context HttpServletRequest request) {
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		Korisnik ulogovaniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		return dao.getManifestacijeZaProdavca(ulogovaniKorisnik.getKorisnickoIme());
	}
	
	@GET
	@Path("/getAktivneManifestacije")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> getAktivneManifestacije() {
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		return dao.getAktivneManifestacije();
	}
	
	@GET
	@Path("/getAktuelneManifestacije")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> getAktuelneManifestacije() {
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		return dao.getAktuelneManifestacije();
	}
	
	@PUT
	@Path("/setOdabranaManifestacija")
	@Consumes(MediaType.TEXT_PLAIN)
	public void setOdabranaManifestacija(String naziv, @Context HttpServletRequest request) {
		request.getSession().setAttribute("odabranaManifestacija", naziv);
	}
	
	@GET
	@Path("/getOdabranaManifestacija")
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestacija getOdabranaManifestacija(@Context HttpServletRequest request) {
		String nazivManifestacije = (String) request.getSession().getAttribute("odabranaManifestacija");
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		return dao.getOdabranaManifestacija(nazivManifestacije);
	}
	
	@GET
	@Path("/getSortiraneManifestacije")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> getSortiraneManifestacije(@QueryParam("kriterijumSortiranja") String kriterijumSortiranja, 
			@QueryParam("kriterijumSortiranja2") String kriterijumSortiranja2) {
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		return dao.getSortiraneManifestacije(kriterijumSortiranja, kriterijumSortiranja2);
	}
}
