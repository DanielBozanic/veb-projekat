package beans;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import utils.CustomImeTipaEnumDeserializer;
import utils.CustomImeTipaEnumSerializer;

public class TipKupca {
	@JsonSerialize(using=CustomImeTipaEnumSerializer.class)
	@JsonDeserialize(using=CustomImeTipaEnumDeserializer.class)
	private ImeTipa imeTipa;
	private double popust;
	private double trazeniBrojBodova;
	
	public TipKupca() {
		
	}
	
	public ImeTipa getImeTipa() {
		return imeTipa;
	}
	
	public void setImeTipa(ImeTipa imeTipa) {
		this.imeTipa = imeTipa;
	}
	
	public double getPopust() {
		return popust;
	}
	
	public void setPopust(double popust) {
		this.popust = popust;
	}
	
	public double getTrazeniBrojBodova() {
		return trazeniBrojBodova;
	}
	
	public void setTrazeniBrojBodova(double trazeniBrojBodova) {
		this.trazeniBrojBodova = trazeniBrojBodova;
	}
}
