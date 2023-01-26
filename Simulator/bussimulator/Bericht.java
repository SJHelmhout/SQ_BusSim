package bussimulator;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;

public class Bericht {
	String lijnNaam;
	String eindpunt;
	String bedrijf;
	String busID;
	int tijd;
	ArrayList<ETA> ETAs;

	Bericht(String lijnNaam, String bedrijf, String busID, int tijd){
		this.lijnNaam=lijnNaam;
		this.bedrijf=bedrijf;
		this.eindpunt="";
		this.busID=busID;
		this.tijd=tijd;
		this.ETAs=new ArrayList<ETA>();
	}

	public static void sendBericht(Bericht bericht){
		XStream xstream = new XStream();
		xstream.alias("Bericht", Bericht.class);
		xstream.alias("ETA", ETA.class);
		String xml = xstream.toXML(bericht);
		Producer producer = new Producer();
		producer.sendBericht(xml);
	}

}
