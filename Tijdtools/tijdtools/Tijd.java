package tijdtools;

public class Tijd implements Time{

	private int uur;
	private int minuut;
	private int seconde;

	public Tijd(){
		this.uur = 0;
		this.minuut = 0;
		this.seconde = 0;
	}

	public Tijd(int uur, int minuut, int seconde) {
		super();
		this.uur = uur;
		this.minuut = minuut;
		this.seconde = seconde;
	}

	@Override
	public int getUur() {
		return uur;
	}

	@Override
	public void setUur(int uur) {
		this.uur = uur;
	}

	@Override
	public int getMinuut() {
		return minuut;
	}

	@Override
	public void setMinuut(int minuut) {
		this.minuut = minuut;
	}

	@Override
	public int getSeconde() {
		return seconde;
	}

	@Override
	public void setSeconde(int seconde) {
		this.seconde = seconde;
	}

	@Override
	public void increment(Time step) {
		this.seconde += step.getSeconde();
		this.minuut += step.getMinuut();
		this.uur += step.getUur();
		if (this.seconde>=60){
			this.seconde-=60;
			this.minuut++;
		}
		if (this.minuut>=60){
			this.minuut-=60;
			this.uur++;
		}
	}

	@Override
	public Tijd copyTijd() {
		return new Tijd(this.uur, this.minuut, this.seconde);
	}

	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", uur,minuut,seconde);
	}
}