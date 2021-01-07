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
	
	@POST
	@Path("/dodajManifestaciju")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean dodajManifestaciju(Manifestacija manifestacija, @Context HttpServletRequest request) throws IOException {
		Manifestacija.InitManifestacija(manifestacija);
		Korisnik ulogovaniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		return dao.dodajManifestaciju(manifestacija, ulogovaniKorisnik.getKorisnickoIme());		
	}
	
	@GET
	@Path("/getManifestacije")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> getManifestacije() {
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		return dao.getManifestacije();
	}
	
	@GET
	@Path("/odobriManifestaciju")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean promeniStatusManifestacije(@QueryParam("naziv") String naziv) throws IOException {
		ManifestacijaDAO dao = (ManifestacijaDAO) ctx.getAttribute("manifestacijaDAO");
		return dao.promeniStatusManifestacije(naziv);
	}
}
