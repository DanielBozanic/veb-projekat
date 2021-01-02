package beans;

public class Lokacija {
	private double geografskaDuzina;
	private double goegrafskaSirina;
	private String ulicaIBroj;
	private String mesto;
	private String postanskiBroj;
	
	public Lokacija() {}

	public double getGeografskaDuzina() {
		return geografskaDuzina;
	}

	public void setGeografskaDuzina(double geografskaDuzina) {
		this.geografskaDuzina = geografskaDuzina;
	}

	public double getGoegrafskaSirina() {
		return goegrafskaSirina;
	}

	public void setGoegrafskaSirina(double goegrafskaSirina) {
		this.goegrafskaSirina = goegrafskaSirina;
	}

	public String getUlicaIBroj() {
		return ulicaIBroj;
	}

	public void setUlicaIBroj(String ulicaIBroj) {
		this.ulicaIBroj = ulicaIBroj;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getPostanskiBroj() {
		return postanskiBroj;
	}

	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}
}
