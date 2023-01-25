package infoborden;

public class JSONBericht {
	private int tijd;
	private int aankomsttijd;
	private String lijnNaam;
	private String busID;
	private String bedrijf;
	private String eindpunt;

	public JSONBericht(int tijd, int aankomsttijd, String lijnNaam, String busID, String bedrijf, String eindpunt) {
		super();
		this.tijd = tijd;
		this.aankomsttijd = aankomsttijd;
		this.lijnNaam = lijnNaam;
		this.busID = busID;
		this.bedrijf = bedrijf;
		this.eindpunt = eindpunt;
	}

	public JSONBericht(){

	}

	public int getTijd() {
		return tijd;
	}

	public void setTijd(int tijd) {
		this.tijd = tijd;
	}

	public int getAankomsttijd() {
		return aankomsttijd;
	}

	public void setAankomsttijd(int aankomsttijd) {
		this.aankomsttijd = aankomsttijd;
	}

	public String getLijnNaam() {
		return lijnNaam;
	}

	public void setLijnNaam(String lijnNaam) {
		this.lijnNaam = lijnNaam;
	}

	public String getBusID() {
		return busID;
	}

	public void setBusID(String busID) {
		this.busID = busID;
	}

	public String getBedrijf() {
		return bedrijf;
	}

	public void setBedrijf(String bedrijf) {
		this.bedrijf = bedrijf;
	}

	public String getEindpunt() {
		return eindpunt;
	}

	public void setEindpunt(String eindpunt) {
		this.eindpunt = eindpunt;
	}

	public String getInfoRegel() {
//		Code voor opdracht 3:
		String tijd = getFormattedTimeFromCounter(aankomsttijd);
		//String tijd = "" + aankomsttijd;
		return String.format("%8s - %5s - %12s", this.lijnNaam, this.eindpunt, tijd);
	}

	@Override
	public String toString() {
		return "JSONBericht [tijd=" + tijd + ", aankomsttijd=" + aankomsttijd + ", lijnNaam=" + lijnNaam + ", busID="
				+ busID + ", bedrijf=" + bedrijf + ", eindpunt=" + eindpunt + "]";
	}

	public String getFormattedTimeFromCounter(int counter){
		int uur = counter/3600;
		int minuten = (counter-3600*uur)/60;
		int seconden = counter - 3600*uur - 60*minuten;
		return String.format("%02d:%02d:%02d", uur,minuten,seconden);
	}
}
