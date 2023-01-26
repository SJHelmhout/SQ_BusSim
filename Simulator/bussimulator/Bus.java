package bussimulator;

import com.thoughtworks.xstream.XStream;
import bussimulator.Halte.Positie;

public class Bus{

	private Bedrijven bedrijf;
	private Lijnen lijn;
	private Route richting;
	private int halteNummer;
	private int totVolgendeHalte;
	private boolean bijHalte;
	private String busID;
	
	Bus(Lijnen lijn, Bedrijven bedrijf, Route richting){
		this.lijn=lijn;
		this.bedrijf=bedrijf;
		this.richting=richting;
		this.halteNummer = -1;
		this.totVolgendeHalte = 0;
		this.bijHalte = false;
		this.busID = "Niet gestart";
	}
	
	public void setbusID(int starttijd){
		this.busID=starttijd+lijn.name()+richting.getRichting();
	}
	
	public void naarVolgendeHalte(){
		Positie volgendeHalte = lijn.getHalte(halteNummer+richting.getRichting()).getPositie();
		totVolgendeHalte = lijn.getHalte(halteNummer).afstand(volgendeHalte);
	}
	
	public boolean halteBereikt(){
		halteNummer+=richting.getRichting();
		bijHalte=true;
		if ((halteNummer>=lijn.getLengte()-1) || (halteNummer == 0)) {
			System.out.printf("Bus %s heeft eindpunt (halte %s, richting %d) bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer)*richting.getRichting());
			return true;
		}
		else {
			System.out.printf("Bus %s heeft halte %s, richting %d bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer)*richting.getRichting());
			naarVolgendeHalte();
		}		
		return false;
	}
	
	public void start() {
		halteNummer = (richting.getRichting()==1) ? 0 : lijn.getLengte()-1;
		System.out.printf("Bus %s is vertrokken van halte %s in richting %d.%n", 
				lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer)*richting.getRichting());
		naarVolgendeHalte();
	}
	
	public boolean move(){
		boolean eindpuntBereikt = false;
		bijHalte=false;
		if (halteNummer == -1) {
			start();
		}
		else {
			totVolgendeHalte--;
			if (totVolgendeHalte==0){
				eindpuntBereikt=halteBereikt();
			}
		}
		return eindpuntBereikt;
	}
	
	public void sendETAs(int nu){
		int i=0;
		Bericht bericht = new Bericht(lijn.name(),bedrijf.name(),busID,nu);
		if (bijHalte) {
			ETA eta = new ETA(lijn.getHalte(halteNummer).name(),lijn.getRichting(halteNummer)*richting.getRichting(),0);
			bericht.ETAs.add(eta);
		}
		Positie eerstVolgende=lijn.getHalte(halteNummer+richting.getRichting()).getPositie();
		int tijdNaarHalte=totVolgendeHalte+nu;
		for (i = halteNummer+richting.getRichting() ; !(i>=lijn.getLengte()) && !(i < 0); i=i+richting.getRichting() ){
			tijdNaarHalte+= lijn.getHalte(i).afstand(eerstVolgende);
			ETA eta = new ETA(lijn.getHalte(i).name(), lijn.getRichting(i)*richting.getRichting(),tijdNaarHalte);
//			System.out.println(bericht.lijnNaam + " naar halte" + eta.halteNaam + " t=" + tijdNaarHalte);
			bericht.ETAs.add(eta);
			eerstVolgende=lijn.getHalte(i).getPositie();
		}
		bericht.eindpunt=lijn.getHalte(i-richting.getRichting()).name();
		sendBericht(bericht);
	}
	
	public void sendLastETA(int nu){
		Bericht bericht = new Bericht(lijn.name(),bedrijf.name(),busID,nu);
		String eindpunt = lijn.getHalte(halteNummer).name();
		ETA eta = new ETA(eindpunt,lijn.getRichting(halteNummer)*richting.getRichting(),0);
		bericht.ETAs.add(eta);
		bericht.eindpunt = eindpunt;
		sendBericht(bericht);
	}

	public void sendBericht(Bericht bericht){
    	XStream xstream = new XStream();
    	xstream.alias("Bericht", Bericht.class);
    	xstream.alias("ETA", ETA.class);
    	String xml = xstream.toXML(bericht);
    	Producer producer = new Producer();
    	producer.sendBericht(xml);		
	}
}
