package tijdtools;

public interface Time {
        int getUur();
        void setUur(int uur);
        int getMinuut();
        void setMinuut(int minuut);
        int getSeconde();
        void setSeconde(int seconde);
        void increment(Time step);
        Time copyTijd();
}

