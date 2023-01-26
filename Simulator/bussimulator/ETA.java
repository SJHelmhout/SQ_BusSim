package bussimulator;

public class ETA {
	String halteNaam;
	int richting;
	int aankomsttijd;
	
	ETA(String halteNaam, int richting, int aankomsttijd){
		this.halteNaam=halteNaam;
		this.richting=richting;
		this.aankomsttijd=aankomsttijd;
	}

	public static void sendLastETA(int nu, Bus bus){
		Bericht bericht = new Bericht(bus.getLijn().name(), bus.getBedrijf().name(), bus.getBusID(),nu);
		String eindpunt = bus.getLijn().getHalte(bus.getHalteNummer()).name();
		ETA eta = new ETA(eindpunt,bus.getLijn().getRichting(bus.getHalteNummer())*bus.getRichting(),0);
		bericht.ETAs.add(eta);
		bericht.eindpunt = eindpunt;
		Bericht.sendBericht(bericht);
	}

	public static void sendETAs(int nu, Bus bus){
		int i;
		Bericht bericht = new Bericht(bus.getLijn().name(), bus.getBedrijf().name(), bus.getBusID(),nu);
		if (bus.isBijHalte()) {
			ETA eta = new ETA(bus.getLijn().getHalte(bus.getHalteNummer()).name(),
					bus.getLijn().getRichting(bus.getHalteNummer())* bus.getRichting(),0);
			bericht.ETAs.add(eta);
		}
		Halte.Positie eerstVolgende= bus.getLijn().getHalte(bus.getHalteNummer()+bus.getRichting()).getPositie();
		int tijdNaarHalte= bus.getTotVolgendeHalte()+nu;
		for (i = bus.getHalteNummer()+bus.getRichting() ;
			 !(i>=bus.getLijn().getLengte()) && !(i < 0); i=i+ bus.getRichting() )
		{
			tijdNaarHalte+= bus.getLijn().getHalte(i).afstand(eerstVolgende);
			ETA eta = new ETA(bus.getLijn().getHalte(i).name(),
					bus.getLijn().getRichting(i)* bus.getRichting(),tijdNaarHalte);
//			System.out.println(bericht.lijnNaam + " naar halte" + eta.halteNaam + " t=" + tijdNaarHalte);
			bericht.ETAs.add(eta);
			eerstVolgende=bus.getLijn().getHalte(i).getPositie();
		}
		bericht.eindpunt=bus.getLijn().getHalte(i-bus.getRichting()).name();
		Bericht.sendBericht(bericht);
	}
}
