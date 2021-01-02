package beans;

public enum Pol {
	MUSKI("muski"),
	ZENSKI("zenski");
	
	String pol;
	
	private Pol(String pol) { this.pol = pol; }
	
	@Override
	public String toString() {
		return this.pol;
	}
	
	public static Pol fromString(String text) {
        for (Pol p : Pol.values()) {
            if (p.toString().equalsIgnoreCase(text)) {
                return p;
            }
        }
        return null;
	}
}
