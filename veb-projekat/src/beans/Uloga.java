package beans;

public enum Uloga {
	ADMINISTRATOR("administrator"),
	PRODAVAC("prodavac"), 
	KUPAC("kupac");
	
	String uloga;
	
	private Uloga(String uloga) { this.uloga = uloga; }
	
	@Override
	public String toString() {
		return this.uloga;
	}
	
	public static Uloga fromString(String text) {
        for (Uloga u : Uloga.values()) {
            if (u.toString().equalsIgnoreCase(text)) {
                return u;
            }
        }
        return null;
	}
}
