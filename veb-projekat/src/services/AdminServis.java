package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import DAO.AdminDAO;
import beans.Karta;
import beans.Korisnik;
import beans.Manifestacija;
import utils.PomocneFunkcije;

@Path("/administratori")
public class AdminServis {
	
	@Context
	ServletContext ctx;
	
	public AdminServis() {
		
	}
	
	@PostConstruct
	public void init() throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		if(ctx.getAttribute("adminDAO") == null) {
			PomocneFunkcije.kreirajBaseFolder();
			ctx.setAttribute("adminDAO", new AdminDAO());
		}
	}
	
	@POST
	@Path("/dodajProdavca")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String dodajProdavca(Korisnik korisnik, @Context HttpServletRequest request) throws IOException {
		Korisnik.InitProdavac(korisnik);
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.dodajProdavca(korisnik);
	}
	
	@PUT
	@Path("/odobriManifestaciju")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> promeniStatusManifestacije(String naziv) throws IOException {
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.promeniStatusManifestacije(naziv);
	}
	
	@DELETE
	@Path("/obrisiKorisnika")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> obrisiKorisnika(String korisnickoIme) throws IOException {
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.obrisiKorisnika(korisnickoIme);
	}
	
	@PUT
	@Path("/blokirajKorisnika")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> blokirajKorisnika(String korisnickoIme) throws IOException {
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.blokirajKorisnika(korisnickoIme);
	}
	
	@PUT
	@Path("/odblokirajKorisnika")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> odblokirajKorisnika(String korisnickoIme) throws IOException {
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.odblokirajKorisnika(korisnickoIme);
	}
	
	@GET
	@Path("/getSumnjiviKupci")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> getSumnjiviKupci() {
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.getSumnjiviKupci();
	}
	
	@PUT
	@Path("/blokirajSumnjivogKupca")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> blokirajSumnjivogKupca(String korisnickoIme) throws IOException {
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.blokirajSumnjivogKupca(korisnickoIme);
	}
	
	@PUT
	@Path("/odblokirajSumnjivogKupca")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> odblokirajSumnjivogKupca(String korisnickoIme) throws IOException {
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.odblokirajSumnjivogKupca(korisnickoIme);
	}
	
	@DELETE
	@Path("/obrisiManifestaciju")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manifestacija> obrisiManifestaciju(String nazivManifestacije) throws IOException {
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.obrisiManifestaciju(nazivManifestacije);
	}
	
	@DELETE
	@Path("/obrisiKartu")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Karta> obrisiKartu(String idKarte) throws IOException {
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.obrisiKartu(idKarte);
	}
}
