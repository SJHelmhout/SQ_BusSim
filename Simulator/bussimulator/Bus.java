package bussimulator;

import com.thoughtworks.xstream.XStream;
import bussimulator.Halte.Positie;

public class Bus{

	private Bedrijven bedrijf;
	private Lijnen lijn;
	private int halteNummer;
	private int totVolgendeHalte;
	private int richting;
	private boolean bijHalte;
	private String busID;
	
	Bus(Lijnen lijn, Bedrijven bedrijf, int richting){
		this.lijn=lijn;
		this.bedrijf=bedrijf;
		this.richting=richting;
		this.halteNummer = -1;
		this.totVolgendeHalte = 0;
		this.bijHalte = false;
		this.busID = "Niet gestart";
	}
	
	public void setbusID(int starttijd){
		this.busID=starttijd+lijn.name()+richting;
	}
	
	public void naarVolgendeHalte(){
		Positie volgendeHalte = lijn.getHalte(halteNummer+richting).getPositie();
		totVolgendeHalte = lijn.getHalte(halteNummer).afstand(volgendeHalte);
	}
	
	public boolean halteBereikt(){
		halteNummer+=richting;
		bijHalte=true;
		if ((halteNummer>=lijn.getLengte()-1) || (halteNummer == 0)) {
			System.out.printf("Bus %s heeft eindpunt (halte %s, richting %d) bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer)*richting);
			return true;
		}
		else {
			System.out.printf("Bus %s heeft halte %s, richting %d bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer)*richting);		
			naarVolgendeHalte();
		}		
		return false;
	}
	
	public void start() {
		halteNummer = (richting==1) ? 0 : lijn.getLengte()-1;
		System.out.printf("Bus %s is vertrokken van halte %s in richting %d.%n", 
				lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer)*richting);		
		naarVolgendeHalte();
	}
	
	public boolean move() {
		boolean eindpuntBereikt = false;
		bijHalte = false;
		if (halteNummer == -1) {
			start();
		} else {
			totVolgendeHalte--;
			if (totVolgendeHalte == 0) {
				eindpuntBereikt = halteBereikt();
			}
		}
		return eindpuntBereikt;
	}

	public Bedrijven getBedrijf() {
		return bedrijf;
	}

	public Lijnen getLijn() {
		return lijn;
	}

	public int getHalteNummer() {
		return halteNummer;
	}

	public int getTotVolgendeHalte() {
		return totVolgendeHalte;
	}

	public int getRichting() {
		return richting;
	}

	public boolean isBijHalte() {
		return bijHalte;
	}

	public String getBusID() {
		return busID;
	}


}
