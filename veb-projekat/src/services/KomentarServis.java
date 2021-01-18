package services;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import DAO.KomentarDAO;
import beans.Komentar;
import utils.PomocneFunkcije;

@Path("/komentari")
public class KomentarServis {
	
	@Context
	ServletContext ctx;
	
	public KomentarServis() {
		
	}
	
	@PostConstruct
	public void init() throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		if(ctx.getAttribute("komentarDAO") == null) {
			PomocneFunkcije.kreirajBaseFolder();
			ctx.setAttribute("komentarDAO", new KomentarDAO());
		}
	}
	
	@POST
	@Path("/objaviKomentar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void objaviKomentar(Komentar komentar) throws IOException {
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentarDAO");
		dao.objaviKomentar(komentar);
	}
}
