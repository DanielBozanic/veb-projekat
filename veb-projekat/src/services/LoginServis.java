package services;

import java.util.AbstractMap.SimpleEntry;
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
import javax.ws.rs.core.Response;

import DAO.LoginDAO;
import beans.Korisnik;

@Path("/loginServis")
public class LoginServis {
	
	@Context
	ServletContext ctx;
	
	public LoginServis() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("loginDAO") == null) {
			ctx.setAttribute("loginDAO", new LoginDAO());
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Korisnik korisnik, @Context HttpServletRequest request) {
		LoginDAO loginDAO = (LoginDAO) ctx.getAttribute("loginDAO");
		Korisnik ulogovaniKorisnik = loginDAO.pronadjiKorisnika(korisnik.getKorisnickoIme(), korisnik.getLozinka());
		if (ulogovaniKorisnik == null) {
			return Response.status(500)
					.type(MediaType.TEXT_PLAIN)
					.entity("Neispravno korisnicko ime i/ili lozinka")
					.build();
		}
		request.getSession().setAttribute("ulogovaniKorisnik", ulogovaniKorisnik);
		return Response.status(200)
				.type(MediaType.TEXT_PLAIN)
				.entity("Uspesno logovanje u sistem.")
				.build();
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	@GET
	@Path("/trenutniKorisnik")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik getTrenutniKorisnik(@Context HttpServletRequest request) {
		return (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
	}
	
	@GET
	@Path("/linkovi")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SimpleEntry<String, String>> getLinkovi(@Context HttpServletRequest request) {
		LoginDAO loginDAO = (LoginDAO) ctx.getAttribute("loginDAO");
		Korisnik trenutniKorisnik = (Korisnik) request.getSession().getAttribute("ulogovaniKorisnik");
		return loginDAO.getLinkovi(trenutniKorisnik);
	}
}