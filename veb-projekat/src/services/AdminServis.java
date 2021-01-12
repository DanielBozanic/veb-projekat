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

import DAO.AdminDAO;
import beans.Korisnik;
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
	public boolean dodajProdavca(Korisnik korisnik, @Context HttpServletRequest request) throws IOException {
		Korisnik.InitProdavac(korisnik);
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.dodajProdavca(korisnik);
	}
	
	@POST
	@Path("/odobriManifestaciju")
	@Consumes(MediaType.TEXT_PLAIN)
	public boolean promeniStatusManifestacije(String naziv) throws IOException {
		AdminDAO dao = (AdminDAO) ctx.getAttribute("adminDAO");
		return dao.promeniStatusManifestacije(naziv);
	}

}
