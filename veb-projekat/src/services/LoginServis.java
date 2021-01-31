package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import DAO.LoginDAO;
import beans.Korisnik;
import utils.PomocneFunkcije;

@Path("/loginServis")
public class LoginServis {
	
	@Context
	ServletContext ctx;
	
	public LoginServis() {
		
	}
	
	@PostConstruct 
	public void init() {
		if (ctx.getAttribute("loginDAO") == null) {
			PomocneFunkcije.kreirajBaseFolder();
			ctx.setAttribute("loginDAO", new LoginDAO());
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Korisnik korisnik, @Context HttpServletRequest request) {
		ctx.setAttribute("loginDAO", new LoginDAO());
		LoginDAO loginDAO = (LoginDAO) ctx.getAttribute("loginDAO");
		Korisnik ulogovaniKorisnik = loginDAO.pronadjiKorisnika(korisnik.getKorisnickoIme(), korisnik.getLozinka());
		if (ulogovaniKorisnik == null) {
			return Response.status(500)
					.type(MediaType.TEXT_PLAIN)
					.entity("Neispravno korisnicko ime i/ili lozinka")
					.build();
		} else if (ulogovaniKorisnik.isBlokiran()) {
			return Response.status(500)
					.type(MediaType.TEXT_PLAIN)
					.entity("Ovaj korisnik je blokiran!")
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
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}
}
