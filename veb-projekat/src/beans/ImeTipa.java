package beans;

public enum ImeTipa {
	ZLATNI("zlatni"), 
	SREBRNI("srebrni"), 
	BRONZANI("bronzani");
	
	String imeTipa;
	
	private ImeTipa(String imeTipa) { this.imeTipa = imeTipa; }
	
	@Override
	public String toString() {
		return this.imeTipa;
	}
	
	public static ImeTipa fromString(String text) {
        for (ImeTipa it : ImeTipa.values()) {
            if (it.toString().equalsIgnoreCase(text)) {
                return it;
            }
        }
        return null;
	}
}
