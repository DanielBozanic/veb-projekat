package beans;

public enum TipKarte {
	VIP("vip"),
	REGULAR("regular"),
	FAN_PIT("fanPit");
	
	String tipKarte;
	
	private TipKarte(String tipKarte) { this.tipKarte = tipKarte; }
	
	@Override
	public String toString() {
		return this.tipKarte;
	}
	
	public static TipKarte fromString(String text) {
        for (TipKarte tk : TipKarte.values()) {
            if (tk.toString().equalsIgnoreCase(text)) {
                return tk;
            }
        }
        return null;
	}
}
