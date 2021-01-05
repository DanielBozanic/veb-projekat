package beans;

public enum TipManifestacije {
	KONCERT("koncert"),
	FESTIVAL("festival"),
	POZORISTE("pozoriste"),
	OSTALO("ostalo");
	
	String tipManifestacije;
	
	private TipManifestacije(String tipManifestacije) { this.tipManifestacije = tipManifestacije; }
	
	@Override
	public String toString() {
		return this.tipManifestacije;
	}
	
	public static TipManifestacije fromString(String text) {
        for (TipManifestacije tm : TipManifestacije.values()) {
            if (tm.toString().equalsIgnoreCase(text)) {
                return tm;
            }
        }
        return null;
	}
}
