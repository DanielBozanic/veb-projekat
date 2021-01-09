package beans;

public enum StatusKarte {
	REZERVISANA("rezervisana"), 
	ODUSTANAK("odustanak");
	
	String statusKarte;
	
	private StatusKarte(String statusKarte) { this.statusKarte = statusKarte; }
	
	@Override
	public String toString() {
		return this.statusKarte;
	}
	
	public static StatusKarte fromString(String text) {
        for (StatusKarte sk : StatusKarte.values()) {
            if (sk.toString().equalsIgnoreCase(text)) {
                return sk;
            }
        }
        return null;
	}
}
