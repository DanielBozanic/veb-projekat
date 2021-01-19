package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
	
	@POST
	@Path("/odobriKomentar")
	@Consumes(MediaType.TEXT_PLAIN)
	public boolean odobriKomentar(String idKomentara) throws IOException {
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentarDAO");
		return dao.odobriKomentar(idKomentara);
	}
	
	@GET
	@Path("/getKomentari")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Komentar> getKomentari() throws IOException {
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentarDAO");
		return dao.getKomentari();
	}
	
	@POST
	@Path("/getOdobreniKomentariZaManifestaciju")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Komentar> getOdobreniKomentariZaManifestaciju(String nazivManifestacije) throws IOException {
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentarDAO");
		return dao.getOdobreniKomentariZaManifestaciju(nazivManifestacije);
	}
	
	@POST
	@Path("/getKomentariZaManifestaciju")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Komentar> getKomentariZaManifestaciju(String nazivManifestacije) throws IOException {
		KomentarDAO dao = (KomentarDAO) ctx.getAttribute("komentarDAO");
		return dao.getKomentariZaManifestaciju(nazivManifestacije);
	}
}
