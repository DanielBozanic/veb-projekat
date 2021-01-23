package beans;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import utils.CustomTipManifestacijeEnumDeserializer;
import utils.CustomTipManifestacijeEnumSerializer;
import utils.LocalDateTimeDeserializer;
import utils.LocalDateTimeSerializer;

public class Manifestacija {
	private String naziv;
	@JsonSerialize(using=CustomTipManifestacijeEnumSerializer.class)
	@JsonDeserialize(using=CustomTipManifestacijeEnumDeserializer.class)
	private TipManifestacije tipManifestacije;
	private int brojMesta;
	@JsonSerialize(using=LocalDateTimeSerializer.class)
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	private LocalDateTime datumIVremeOdrzavanja;
	private BigDecimal cenaRegularKarte;
	private boolean aktivan;
	private Lokacija lokacija;
	private String posterManifestacije;
	private boolean obrisana;
	private double prosecnaOcena;
	
	public Manifestacija() {}
	
	public static void InitManifestacija(Manifestacija manifestacija)	{
		manifestacija.aktivan = false;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipManifestacije getTipManifestacije() {
		return tipManifestacije;
	}

	public void setTipManifestacije(TipManifestacije tipManifestacije) {
		this.tipManifestacije = tipManifestacije;
	}

	public int getBrojMesta() {
		return brojMesta;
	}

	public void setBrojMesta(int brojMesta) {
		this.brojMesta = brojMesta;
	}

	public LocalDateTime getDatumIVremeOdrzavanja() {
		return datumIVremeOdrzavanja;
	}

	public void setDatumIVremeOdrzavanja(LocalDateTime datumIVremeOdrzavanja) {
		this.datumIVremeOdrzavanja = datumIVremeOdrzavanja;
	}

	public BigDecimal getCenaRegularKarte() {
		return cenaRegularKarte;
	}

	public void setCenaRegularKarte(BigDecimal cenaRegularKarte) {
		this.cenaRegularKarte = cenaRegularKarte;
	}

	public Lokacija getLokacija() {
		return lokacija;
	}

	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}

	public String getPosterManifestacije() {
		return posterManifestacije;
	}

	public void setPosterManifestacije(String posterManifestacije) {
		this.posterManifestacije = posterManifestacije;
	}	
	
	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	public boolean isObrisana() {
		return obrisana;
	}

	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}

	public double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}
}
