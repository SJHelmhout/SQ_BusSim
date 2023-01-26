package bussimulator;

public class BusFactory {

    public Bus createBus(Lijnen lijn, Bedrijven bedrijf, Route richting) {
        return new Bus(lijn, bedrijf, richting);
    }
}
