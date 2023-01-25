package tijdtools;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class TijdFuncties {
	private static Tijd simulatorTijd;
	private static Tijd verschil;
	private static int interval;
	private static int syncInterval;
	private static int syncCounter;


	public static void initSimulatorTijden(int interval, int syncInterval){
		simulatorTijd =new Tijd(0,0,0);
		Tijd startTijd = getCentralTime();
		verschil= berekenVerschil(startTijd,simulatorTijd);
		TijdFuncties.interval =interval;
		syncCounter=syncInterval;
		TijdFuncties.syncInterval =syncInterval;
	}

	public static String getSimulatorWeergaveTijd(){
		Tijd simulatorWeergaveTijd = simulatorTijd.copyTijd();
		simulatorWeergaveTijd.increment(verschil);
		return simulatorWeergaveTijd.toString();
	}

	public static int getCounter(){
		return calculateCounter(simulatorTijd);
	}

	public static int getTijdCounter(){
		return calculateCounter(simulatorTijd)+calculateCounter(verschil);
	}

	public static void simulatorStep() throws InterruptedException{
		Thread.sleep(interval);
		simulatorTijd.increment(new Tijd(0,0,1));
		syncCounter--;
		if (syncCounter==0){
			syncCounter=syncInterval;
			synchroniseTijd();
		}
	}

	public static int calculateCounter(Tijd tijd){
		return tijd.getUur()*3600+tijd.getMinuut()*60+tijd.getSeconde();
	}

	public static Tijd berekenVerschil(Tijd reverentieTijd, Tijd werkTijd){
		int urenVerschil = reverentieTijd.getUur()-werkTijd.getUur();
		int minutenVerschil = reverentieTijd.getMinuut()-werkTijd.getMinuut();
		int secondenVerschil = reverentieTijd.getSeconde()-werkTijd.getSeconde();
		if (secondenVerschil<0){
			minutenVerschil--;
			secondenVerschil+=60;
		}
		if (minutenVerschil<0){
			urenVerschil--;
			minutenVerschil+=60;
		}
		return new Tijd(urenVerschil, minutenVerschil, secondenVerschil);
	}

	public static void synchroniseTijd(){
		Tijd huidigeTijd = getCentralTime();
		System.out.println("De werkelijke tijd is nu: "+ huidigeTijd.toString());
		Tijd verwachtteSimulatorTijd = simulatorTijd.copyTijd();
		verwachtteSimulatorTijd.increment(verschil);
		Tijd delay = berekenVerschil(huidigeTijd, verwachtteSimulatorTijd);
		verschil.increment(delay);
	}

	public static Tijd getCentralTime()
	{
		try {
			HTTPFuncties httpFuncties = new HTTPFuncties();
			String result = httpFuncties.executeGet("json");
			return new ObjectMapper().readValue(result, Tijd.class);
		} catch (IOException e) {
			e.printStackTrace();
			return new Tijd(0,0,0);
		}
	}




}