package beans;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import utils.CustomStatusKarteEnumDeserializer;
import utils.CustomStatusKarteEnumSerializer;
import utils.CustomTipKarteEnumDeserializer;
import utils.CustomTipKarteEnumSerializer;
import utils.LocalDateTimeDeserializer;
import utils.LocalDateTimeSerializer;

public class Karta {
	private String identifikatorKarte;
	private Manifestacija manifestacija;
	@JsonSerialize(using=LocalDateTimeSerializer.class)
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	private LocalDateTime datumIVremeManifestacije;
	private BigDecimal cena;
	private String kupac;
	@JsonSerialize(using=CustomStatusKarteEnumSerializer.class)
	@JsonDeserialize(using=CustomStatusKarteEnumDeserializer.class)
	private StatusKarte statusKarte;
	@JsonSerialize(using=CustomTipKarteEnumSerializer.class)
	@JsonDeserialize(using=CustomTipKarteEnumDeserializer.class)
	private TipKarte tipKarte;
	private int brojKarata;
	
	public Karta() {}
	
	public String getIdentifikatorKarte() {
		return identifikatorKarte;
	}

	public void setIdentifikatorKarte(String identifikatorKarte) {
		this.identifikatorKarte = identifikatorKarte;
	}

	public Manifestacija getManifestacija() {
		return manifestacija;
	}

	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}

	public LocalDateTime getDatumIVremeManifestacije() {
		return datumIVremeManifestacije;
	}

	public void setDatumIVremeManifestacije(LocalDateTime datumIVremeManifestacije) {
		this.datumIVremeManifestacije = datumIVremeManifestacije;
	}

	public BigDecimal getCena() {
		return cena;
	}

	public void setCena(BigDecimal cena) {
		this.cena = cena;
	}

	public String getKupac() {
		return kupac;
	}

	public void setKupac(String kupac) {
		this.kupac = kupac;
	}

	public StatusKarte getStatusKarte() {
		return statusKarte;
	}

	public void setStatusKarte(StatusKarte statusKarte) {
		this.statusKarte = statusKarte;
	}

	public TipKarte getTipKarte() {
		return tipKarte;
	}

	public void setTipKarte(TipKarte tipKarte) {
		this.tipKarte = tipKarte;
	}

	public int getBrojKarata() {
		return brojKarata;
	}

	public void setBrojKarata(int brojKarata) {
		this.brojKarata = brojKarata;
	}
}
