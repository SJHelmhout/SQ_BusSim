package infoborden;

import java.io.IOException;
import java.util.HashMap;
import org.codehaus.jackson.map.ObjectMapper;
public class Berichten {

	private final HashMap<String,Integer> laatsteBericht = new HashMap<>();
	private final HashMap<String,JSONBericht> infoBordRegels = new HashMap<>();
	private int hashValue;
	private boolean refresh;
	private String[] infoTekstRegels;

	private int aantalRegels;
	private int[] aankomsttijden;
	
	public void nieuwBericht(String incoming) {
		try {
			JSONBericht bericht = new ObjectMapper().readValue(incoming, JSONBericht.class);
	    	String busID = bericht.getBusID();
	    	Integer tijd = bericht.getTijd();
	    	if (!laatsteBericht.containsKey(busID) || laatsteBericht.get(busID)<=tijd){
	    		laatsteBericht.put(busID, tijd);
	    		if (bericht.getAankomsttijd()==0){
	    			infoBordRegels.remove(busID);
	    		} else {
	    			infoBordRegels.put(busID, bericht);
	    		}
	    	}
	    	setRegels();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setRegels(){
		aantalRegels = 0;
		String[] infoTekst = new String[]{"--1--", "--2--", "--3--", "--4--", "leeg"};
		aankomsttijden=new int[5];
		if(!infoBordRegels.isEmpty()){
			for(String busID: infoBordRegels.keySet()){
				JSONBericht regel = infoBordRegels.get(busID);
				addRegel(regel, infoTekst);
			}
		}
		refresh = checkRepaint();
		infoTekstRegels = infoTekst;
	}

	public void addRegel(JSONBericht regel, String[] infoTekst){
		int dezeTijd=regel.getAankomsttijd();
		String dezeTekst=regel.getInfoRegel();
		int plaats=aantalRegels;
		for(int i=aantalRegels;i>0;i--){
			if(dezeTijd<aankomsttijden[i-1]){
				aankomsttijden[i]=aankomsttijden[i-1];
				infoTekst[i]=infoTekst[i-1];
				plaats=i-1;
			}
		}
		aankomsttijden[plaats]=dezeTijd;
		infoTekst[plaats]=dezeTekst;
		if(aantalRegels<4){ aantalRegels++; }
	}
	
	private boolean checkRepaint(){
		int totaalTijden=0;
		for(int i=0; i<this.aantalRegels;i++){
			totaalTijden+=this.aankomsttijden[i];
		}
		if(hashValue!=totaalTijden){
			hashValue=totaalTijden;
			return true;
		}
		return false;
	}
	
	public boolean hetBordMoetVerverst() {
		return refresh;
	}
	
	public String[] repaintInfoBordValues(){
		return infoTekstRegels;
	}
}
